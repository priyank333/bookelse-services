/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.mgr;

import com.catalog.dao.ProductDao;
import com.catalog.dao.StockItemDao;
import com.catalog.exception.OperationError;
import com.catalog.exception.ResourceNotFoundException;
import com.catalog.model.ServiceResponse;
import com.catalog.model.StockItem;
import com.catalog.repository.StockItemRepository;
import com.catalog.requestpayload.StockItemViewPayload;
import com.catalog.requestpayload.StockPayload;
import com.catalog.util.ProductServiceUtil;
import static com.catalog.util.ProductServiceUtil.sumOfArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class StockControllerMgr {

    @Autowired
    private StockItemDao stockItemDao;

    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private ProductDao productDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(StockControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse addStock(StockPayload stockPayload) {
        LOGGER.info("Adding stock with payload: {}", stockPayload);
        String productId = stockPayload.product.getProductId();
        boolean isProductExist = productDao.existsById(productId);
        if (!isProductExist) {
            throw new ResourceNotFoundException("product", productId, "Product is not found");
        }
        int totalQuantity = ProductServiceUtil.getTotalCountFromStock(stockPayload.stockItems);
        int existingCount = productDao.getQuantity(productId);
        totalQuantity += existingCount;
        int rowAffected = productDao.setProductQty(totalQuantity, productId);
        if (rowAffected != 0) {
            LOGGER.info("Product quantity is updated productId: {} - quantity: {}", productId,
                    totalQuantity);
        }
        List<StockItem> stockItems = getStockItemsFromPayload(stockPayload);
        Iterable<StockItem> iterableItems = stockItems;
        Iterable<StockItem> savedItemsIterable = stockItemDao.saveAll(iterableItems);
        List<StockItem> savedItems = new ArrayList<>();
        savedItemsIterable.forEach(savedItems::add);
        ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.CREATED.value());
        serviceResponse.setResponse(savedItems);
        return serviceResponse;
    }

    private List<StockItem> getStockItemsFromPayload(StockPayload stockPayload) {
        List<StockItem> stockItems = new ArrayList<>();
        for (com.catalog.requestpayload.StockItem stockItemInput : stockPayload.stockItems) {
            for (int counter = 0; counter < stockItemInput.purchasedQuantity; counter++) {
                StockItem stockItem = new StockItem();
                stockItem.setIsProductActive(true);
                stockItem.setIsProductReturnedToVendor(false);
                stockItem.setIsProductSold(false);
                stockItem.setProduct(stockPayload.product);
                stockItem.setPurchasedOn(stockItemInput.purchasedOn);
                stockItem.setPurchasedPrice(stockItemInput.purchasedPrice);
                stockItem.setVendor(stockItemInput.vendor);
                stockItems.add(stockItem);
            }
        }
        return stockItems;
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateStockItem(StockItem stockItem) {
        ServiceResponse serviceResponse = new ServiceResponse();
        StockItem existingStockItem = getExistingStockItem(stockItem.getStockItemId());
        setUpdatableParams(existingStockItem, stockItem);
        if (stockItemDao.save(existingStockItem) != null) {
            serviceResponse.setStatusCode(HttpStatus.OK.value());
            serviceResponse.setResponse(true);
        } else {
            serviceResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            serviceResponse.setResponse(false);
        }
        return serviceResponse;
    }

    private void setUpdatableParams(StockItem existingStockItem, StockItem stockItem) {
        existingStockItem.setPurchasedOn(stockItem.getPurchasedOn());
        existingStockItem.setVendor(stockItem.getVendor());
        existingStockItem.setPurchasedPrice(stockItem.getPurchasedPrice());
    }

    private StockItem getExistingStockItem(Long stockItemId) throws ResourceNotFoundException {
        Optional<StockItem> existingItem = stockItemDao.findById(stockItemId);
        if (!existingItem.isPresent()) {
            throw new ResourceNotFoundException("Stock Item", stockItemId, "Stock item is not found");

        }
        return existingItem.get();
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteStockItem(Long stockItemId) {
        String productId = stockItemRepository.getProductIdByStock(stockItemId);
        if (productId == null) {
            throw new ResourceNotFoundException("Stock Item", stockItemId, "Stock item is not found");
        }

        if (stockItemDao.getIsProductSoldByStockItem(stockItemId)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("stockItemId", stockItemId);
            throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY, "StockItem is already sold", params,
                    "DeleteStockItem");
        }

        stockItemDao.deleteById(stockItemId);

        LOGGER.info("ProductId :: {}", productId);
        productDao.deductProductQty(1, productId);
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(HttpStatus.OK.value());
        serviceResponse.setResponse(true);
        return serviceResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateIsProductSold(List<Long> stockItemList, Boolean isProductSold,
            Boolean updateQuantity, String productId) {
        LOGGER.info("Updating isProductSold with value "
                + "{stockItemList: {}, isProductSold: {}, updateQuantity: {}, " + "productId: {}}",
                stockItemList, isProductSold, updateQuantity, productId);
        Map<Long, Boolean> isSoldValues = stockItemRepository.listIsProductSoldByStockItems(stockItemList);
        if (stockItemList.size() != isSoldValues.size()) {
            throw new ResourceNotFoundException("StockItems", stockItemList,
                    "Stock items input are not matched with existing records");
        }
        List<String> productIdList = stockItemRepository.listProductIdByStockItems(stockItemList);
        if (productIdList.size() > 1 || !productIdList.get(0).equals(productId)) {
            throw new ResourceNotFoundException("product", productId, "Product is not found");
        }
        for (Map.Entry<Long, Boolean> entry : isSoldValues.entrySet()) {
            if (Objects.equals(isProductSold, entry.getValue())) {
                throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Invalid Operation. " + "Already one or more than one items status are "
                        + "updated with requested value.",
                        "UpdateIsProductSold");
            }
        }

        int rowAffected = sumOfArray(
                stockItemRepository.batchUpdateStockItemsIsSold(stockItemList, isProductSold));
        Boolean isFlagUpdated = rowAffected == stockItemList.size();
        LOGGER.info("isFlagUpdated: {}", isFlagUpdated);
        if (updateQuantity && !isProductSold && isFlagUpdated) {
            int productCount = productDao.getQuantity(productId);
            productDao.setProductQty(productCount + rowAffected, productId);
        } else if (updateQuantity && isProductSold && isFlagUpdated) {
            int productCount = productDao.getQuantity(productId);
            productDao.setProductQty(productCount - rowAffected, productId);
        }
        ServiceResponse serviceResponse = new ServiceResponse();

        if (isFlagUpdated) {
            serviceResponse.setStatusCode(HttpStatus.OK.value());
            serviceResponse.setResponse(true);
        } else {
            serviceResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            serviceResponse.setResponse(false);
        }
        return serviceResponse;
    }

    public ServiceResponse listStockItems(StockItemViewPayload itemViewPayload) {
        List<StockItem> stockItems = stockItemRepository.listStockItems(itemViewPayload);
        ServiceResponse serviceResponse = new ServiceResponse();
        if (stockItems.isEmpty()) {
            serviceResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
        } else {
            serviceResponse.setStatusCode(HttpStatus.OK.value());
            serviceResponse.setResponse(stockItems);
        }
        return serviceResponse;
    }

    public ServiceResponse makeStockInactive(Long stockItemId) {
        Boolean isStockItemExist = stockItemDao.existsById(stockItemId);
        if (!isStockItemExist) {
            throw new ResourceNotFoundException("MakeStockInactive", stockItemId,
                    "Stock item is not found");
        }
        Boolean currentValIsPrdActive = stockItemDao.getIsProductActive(stockItemId);
        if (currentValIsPrdActive) {
            Boolean isProductSold = stockItemDao.getIsProductSoldByStockItem(stockItemId);
            if (isProductSold) {
                throw new OperationError(HttpStatus.PRECONDITION_FAILED,
                        "Stock item is already sold " + "not allowed to inactive stock.",
                        "MakeStockInactive");
            }
            Boolean isUpdated = stockItemDao.updateIsProductActive(stockItemId, Boolean.FALSE) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the value", "MakeStockInactive");
            }
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(HttpStatus.OK.value());
        serviceResponse.setResponse(true);
        return serviceResponse;
    }

    public ServiceResponse makeStockActive(Long stockItemId) {
        Boolean isStockItemExist = stockItemDao.existsById(stockItemId);
        if (!isStockItemExist) {
            throw new ResourceNotFoundException("MakeStockActive", stockItemId, "Stock item is not found");
        }
        Boolean currentValIsPrdActive = stockItemDao.getIsProductActive(stockItemId);
        if (!currentValIsPrdActive) {
            Boolean isUpdated = stockItemDao.updateIsProductActive(stockItemId, Boolean.TRUE) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the value", "MakeStockActive");
            }
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(HttpStatus.OK.value());
        serviceResponse.setResponse(true);
        return serviceResponse;
    }

    public ServiceResponse returnStockToVendor(Long stockItemId) {
        Boolean isStockItemExist = stockItemDao.existsById(stockItemId);
        if (!isStockItemExist) {
            throw new ResourceNotFoundException("ReturnStockToVendor", stockItemId,
                    "Stock item is not found");
        }
        Boolean currentValRtnStkToVendor = stockItemDao.getIsProductReturnedToVendor(stockItemId);
        if (!currentValRtnStkToVendor) {
            Boolean isProductSold = stockItemDao.getIsProductSoldByStockItem(stockItemId);
            if (isProductSold) {
                throw new OperationError(HttpStatus.PRECONDITION_FAILED,
                        "Stock item is already sold " + "not allowed to returned to vendor.",
                        "ReturnStockToVendor");
            }
            Boolean isUpdated = stockItemDao.updateIsProductActive(stockItemId, Boolean.FALSE) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the value IsProductActive.", "ReturnStockToVendor");
            }

            isUpdated = stockItemDao.updateIsProductReturnToVendor(stockItemId, Boolean.TRUE) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the value IsProductReturnToVendor.",
                        "ReturnStockToVendor");
            }
            String productId = stockItemRepository.getProductIdByStock(stockItemId);
            int productCount = productDao.getQuantity(productId);
            isUpdated = productDao.setProductQty(productCount - 1, productId) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the product quantity.", "ReturnStockToVendor");
            }
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(HttpStatus.OK.value());
        serviceResponse.setResponse(true);
        return serviceResponse;
    }

    public ServiceResponse getReturnedStockFromVendor(Long stockItemId) {
        Boolean isStockItemExist = stockItemDao.existsById(stockItemId);
        if (!isStockItemExist) {
            throw new ResourceNotFoundException("GetStockFromVendor", stockItemId,
                    "Stock item is not found");
        }
        Boolean currentValRtnStkToVendor = stockItemDao.getIsProductReturnedToVendor(stockItemId);
        if (currentValRtnStkToVendor) {
            Boolean isProductSold = stockItemDao.getIsProductSoldByStockItem(stockItemId);
            if (isProductSold) {
                throw new OperationError(HttpStatus.PRECONDITION_FAILED,
                        "Stock item is already sold " + "not allowed to get stock from vendor.",
                        "GetStockFromVendor");
            }
            Boolean isUpdated = stockItemDao.updateIsProductActive(stockItemId, Boolean.TRUE) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the value IsProductActive.", "GetStockFromVendor");
            }

            isUpdated = stockItemDao.updateIsProductReturnToVendor(stockItemId, Boolean.FALSE) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the value IsProductReturnToVendor.",
                        "GetStockFromVendor");
            }

            String productId = stockItemRepository.getProductIdByStock(stockItemId);
            int productCount = productDao.getQuantity(productId);
            isUpdated = productDao.setProductQty(productCount + 1, productId) != 0;
            if (!isUpdated) {
                throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Not able to update the product quantity.", "GetStockFromVendor");
            }
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(HttpStatus.OK.value());
        serviceResponse.setResponse(true);
        return serviceResponse;
    }
}
