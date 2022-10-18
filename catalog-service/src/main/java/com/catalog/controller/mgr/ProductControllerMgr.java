/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.mgr;

import com.catalog.dao.ProductDao;
import com.catalog.exception.OperationError;
import com.catalog.exception.ResourceNotFoundException;
import com.catalog.model.Product;
import com.catalog.model.ServiceResponse;
import com.catalog.model.StockItem;
import com.catalog.repository.ProductRepository;
import com.catalog.repository.StockItemRepository;
import com.catalog.requestpayload.ProductAttributePayload;
import static com.catalog.util.ProductServiceUtil.generateProductId;
import static com.catalog.util.ProductServiceUtil.sumOfArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ProductControllerMgr {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImageControllerMgr imageControllerMgr;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private StockItemRepository stockItemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public String addProduct(final Product product) {
        LOGGER.info("Adding product with value: {}", product);
        synchronized (this) {
            product.setReserveQuantity(0);
        }
        String productId = generateProductId();
        product.setProductId(productId);
        product.setQuantity(0);
        Product savedProduct = productDao.save(product);
        return savedProduct.getProductId();
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateProductDetails(Product product) {
        Integer existingQuantity = productDao.getQuantity(product.getProductId());
        if (!productDao.existsById(product.getProductId())) {
            Map<String, String> requestedValue = new HashMap<>();
            requestedValue.put("productId", product.getProductId());
            throw new ResourceNotFoundException("Product", requestedValue, "Product is not found");
        } else {
            product.setQuantity(existingQuantity);
        }
        if (null != productDao.save(product)) {
            LOGGER.info("Product is updated with new value: {}", product);
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteProduct(final String productId) {
        Optional<Product> product = productDao.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("product", productId, "Product is not exist.");
        }
        LOGGER.info("product is found : {}", product);
        Map<Long, Boolean> stockItems = stockItemRepository.listIsProductSoldByProduct(productId);
        checkIsProductSold(stockItems, productId);
        Product productToBeDeleted = product.get();
        boolean isDeleted = stockItemRepository.deleteItemStockByProduct(productToBeDeleted.getProductId());
        if (isDeleted) {
            LOGGER.info("Stock is updated: Stock items are deleted by " + "productId: {}", productId);
        }
        if (productToBeDeleted.getProductImages() != null) {
            productToBeDeleted.getProductImages().forEach((image) -> {
                imageControllerMgr.deleteProductImage(image.getProductImageId());
            });
        }
        productDao.deleteById(productId);
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    private void checkIsProductSold(Map<Long, Boolean> stockItems, final String productId) throws OperationError {
        LOGGER.info("values: {}", stockItems);
        for (Map.Entry<Long, Boolean> stockItem : stockItems.entrySet()) {
            if (stockItem.getValue()) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("productId", productId);
                throw new OperationError(HttpStatus.PRECONDITION_FAILED,
                        "This item is not eligible " + "for delete because some set of products are sold.", parameters,
                        "DeleteProduct");
            }
        }
    }

    public Product getProductDetailsById(final String productId) {
        Optional<Product> product = productDao.findById(productId);
        if (product.isPresent()) {
            return product.get();
        } else {
            return null;
        }
    }

    public Boolean isProductQuantityAvailable(String productId, Integer quantity) {
        Boolean isQuantityAvailable = (productDao.getQuantity(productId) - quantity) >= 0;
        return isQuantityAvailable;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean releaseQuantity(Map<String, Integer> productList) {
        boolean isOperationExecuted = true;
        for (Map.Entry<String, Integer> product : productList.entrySet()) {
            Boolean isReleased = productDao.releaseQuantity(product.getKey(), product.getValue()) > 0;
            if (!isReleased) {
                isOperationExecuted = false;
            }
        }
        return isOperationExecuted;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean reserveQuantity(Map<String, Integer> productList) {
        Boolean isOperationExecuted = true;
        for (Map.Entry<String, Integer> product : productList.entrySet()) {
            Boolean isQuantityAvailable = isProductQuantityAvailable(product.getKey(), product.getValue());
            if (isQuantityAvailable) {
                Boolean isQuantityReserved = productDao.reserveQuantity(product.getKey(), product.getValue()) > 0;
                if (!isQuantityReserved) {
                    isOperationExecuted = false;
                }
            } else {
                isOperationExecuted = false;
            }
        }
        return isOperationExecuted;
    }

    public ServiceResponse listProductsForClient(ProductAttributePayload productAttributePayload) {
        List<Product> listProducts = listProducts(productAttributePayload);
        ServiceResponse serviceResponse = new ServiceResponse();
        if (listProducts.isEmpty()) {
            serviceResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
        } else {
            listProducts.stream().map(product -> {
                product.setIsAvailable(product.getQuantity() - product.getReserveQuantity() > 0);
                return product;
            }).map(product -> {
                product.setQuantity(null);
                return product;
            }).forEachOrdered(product -> {
                product.setReserveQuantity(null);
            });
            serviceResponse.setResponse(listProducts);
            serviceResponse.setStatusCode(HttpStatus.OK.value());
        }
        return serviceResponse;
    }

    public ServiceResponse listProductsForAdmin(
            ProductAttributePayload productAttributePayload) {
        List<Product> listProducts = listProducts(productAttributePayload);
        ServiceResponse serviceResponse = new ServiceResponse();
        if (listProducts.isEmpty()) {
            serviceResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
        } else {
            serviceResponse.setResponse(listProducts);
            serviceResponse.setStatusCode(HttpStatus.OK.value());
        }
        return serviceResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    private List<Product> listProducts(ProductAttributePayload productAttributePayload) {
        List<Product> productList = new ArrayList<>();
        if (productAttributePayload.showBook != null && productAttributePayload.showBook) {
            productList.addAll(productRepo.listBooks(productAttributePayload));
        }
        if (productAttributePayload.showBookSet != null && productAttributePayload.showBookSet) {
            productList.addAll(productRepo.listBookSet(productAttributePayload));
        }
        if (productAttributePayload.showRentalBook != null && productAttributePayload.showRentalBook) {
            productList.addAll(productRepo.listRentalBook(productAttributePayload));
        }

        if (productAttributePayload.showRentalBookSet != null && productAttributePayload.showRentalBookSet) {
            productList.addAll(productRepo.listRentalBookSets(productAttributePayload));
        }
        return productList;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized ServiceResponse deductStock(String productId, Integer quantity) {
        if (!productDao.existsById(productId)) {
            throw new ResourceNotFoundException("product", productId, "Product is not found.");
        }
        int currentStockCount = productDao.getActualQuantity(productId);
        if (currentStockCount - quantity < 0) {
            Map<String, Object> params = new HashMap<>();
            params.put("productId", productId);
            params.put("quantity", quantity);
            throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY, "Product is not available", params,
                    "DeductStock");
        }
        int rowAffected = productDao.setProductQty(currentStockCount - quantity, productId);
        List<StockItem> stockItems = stockItemRepository.listUnsoldActiveStockItemsByProductId(productId, quantity);
        LOGGER.info("StockItems :: {}", stockItems);
        List<Long> stockItemAsLong = getStockItemsAsLong(stockItems);
        Boolean isFlagUpdated = sumOfArray(
                stockItemRepository.batchUpdateStockItemsIsSold(stockItemAsLong, true)) == stockItemAsLong.size();
        if (isFlagUpdated) {
            LOGGER.info("isProductSold is updated as true for productId: {}", productId);
        }
        if (rowAffected != 0) {
            LOGGER.info("Total count is updated for productId: {}", productId);
        }
        ServiceResponse serviceResponse = new ServiceResponse();
        if (isFlagUpdated && rowAffected != 0) {
            serviceResponse.setStatusCode(HttpStatus.OK.value());
            serviceResponse.setResponse(stockItems);
        } else {
            serviceResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            serviceResponse.setResponse("Unable to deduct stock.");
        }
        return serviceResponse;
    }

    private List<Long> getStockItemsAsLong(List<StockItem> stockItems) {
        List<Long> stockItemsAsLong = new ArrayList<>();
        stockItems.forEach(stockItem -> {
            stockItemsAsLong.add(stockItem.getStockItemId());
        });
        return stockItemsAsLong;
    }

}
