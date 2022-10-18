/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.mgr;

import com.coms.dao.CustomerOrderDao;
import com.coms.dao.RentalProductDao;
import com.coms.dao.ReturnRequestDao;
import com.coms.dao.SoldProductDao;
import com.coms.model.RentalProduct;
import com.coms.model.ReturnRequest;
import com.coms.model.ReturnStatus;
import com.coms.model.ReturnType;
import com.coms.dto.ServiceResponse;
import com.coms.exception.OperationError;
import com.coms.exception.ResourceNotFoundException;
import com.coms.model.OrderStatus;
import com.coms.model.SoldProduct;
import com.coms.payload.ReturnRequestPayload;
import com.coms.repository.RentalProductRepository;
import com.coms.repository.ReturnProductRepository;
import com.coms.repository.ReturnRequestRepository;
import com.coms.repository.SoldProductRepository;
import com.coms.service.ProductService;
import static com.coms.util.Constant.LOCK_REASON_CREATE_RET_REQ;
import com.coms.util.OrderServiceUtil;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author z0043uwn
 */
@Service
public class ReturnableProductControllerMgr {

    @Autowired
    private ReturnRequestDao returnRequestDao;
    @Autowired
    private SoldProductDao soldProductDao;
    @Autowired
    private RentalProductDao rentalProductDao;
    @Autowired
    private RentalProductRepository rentalProductRepository;
    @Autowired
    private SoldProductRepository soldProductRepo;
    @Autowired
    private ReturnProductRepository returnProductRepository;
    @Autowired
    private CustomerOrderDao orderDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private ReturnRequestRepository returnRequestRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(
            RentalProductControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse createReturnProductRequest(
            Long soldProductId, ReturnType returnType, String orderNumber) {
        ServiceResponse serviceResponse = new ServiceResponse();
        LOGGER.info("Creating return request for soldProductId: {},"
                + " returnType : {}, orderNumber: {}",
                soldProductId, returnType, orderNumber);
        SoldProduct soldProduct = soldProductRepo.
                getSoldProductForReturn(soldProductId, orderNumber);
        isProductExist(soldProduct, soldProductId);
        isProductMapWithOrder(
                soldProduct,
                orderNumber,
                soldProductId,
                returnType);
        LocalDate deliveredOn = isOrderDelivered(
                soldProduct,
                orderNumber,
                returnType,
                soldProductId);
        isProdutReturned(soldProduct, soldProductId, orderNumber, returnType);
        isReturnRequestOpen(soldProduct, soldProductId, orderNumber, returnType);
        ReturnRequest returnRequest = new ReturnRequest();
        String customerId = orderDao.getCustomerIdByOrder(orderNumber);
        if (returnType == ReturnType.RETURN_PRODUCT) {
            isProductWithinReturnPeriod(
                    deliveredOn,
                    soldProduct,
                    soldProductId,
                    orderNumber,
                    returnType);
            double netPrice = OrderServiceUtil.calculateNetPrice(
                    soldProduct.getSellPrice(), soldProduct.getDiscount());
            setReturnRequest(returnRequest,
                    soldProductId, returnType, netPrice, customerId);
        } else if (returnType == ReturnType.RETURN_RENTAL_PRODUCT) {
            RentalProduct rentalProduct = rentalProductRepository.
                    getRentalProductBySoldProduct(soldProductId);
            isEligibleForReturn(
                    rentalProduct,
                    soldProductId,
                    returnType,
                    orderNumber);
            Double netRefundAmount
                    = rentalProduct.getRefundAmount()
                    - rentalProduct.getDelayCharge();
            setReturnRequest(returnRequest,
                    soldProductId, returnType, netRefundAmount, customerId);
            LOGGER.info("Refund amount for soldProductId: {} refundAmount: {}",
                    soldProductId, netRefundAmount);
        }
        LOGGER.info("SoldProductID :: {}", soldProduct.getSoldProductId());
        Boolean lockStatus = rentalProductDao.
                updateLockStatusBySPID(Boolean.TRUE,
                        LOCK_REASON_CREATE_RET_REQ,
                        soldProduct.getSoldProductId()) > 0;
        LOGGER.info("LockStatus Result :: {}", lockStatus);
        returnRequest.setIsAmountPaidToCustomer(false);
        soldProductDao.updateReturnRequestOpenStatus(soldProductId, true);
        String returnRequestId = returnRequestDao.save(returnRequest).
                getReturnRequestId().toString();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("returnRequestId", returnRequestId);
        LOGGER.info("Return request is genereted with returnRequestId: {}"
                + "for soldProductId: {},"
                + " returnType: {}", returnRequestId, soldProductId, returnType);
        serviceResponse.setStatusCode(HttpStatus.CREATED.value());
        serviceResponse.setResponse(linkedHashMap);
        return serviceResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    private void isEligibleForReturn(
            RentalProduct rentalProduct,
            Long soldProductId,
            ReturnType returnType,
            String orderNumber) throws OperationError {
        if (!rentalProduct.getIsEligibleForReturn()) {
            LOGGER.info("soldProductId: {} is not eligible for return. "
                    + "ReturnType: {}",
                    soldProductId, returnType);
            Map<String, Object> resourceValue = new HashMap<>();
            resourceValue.put("orderNumber", orderNumber);
            resourceValue.put("returnType", returnType);
            resourceValue.put("soldProductId", soldProductId);
            throw new OperationError(
                    HttpStatus.PRECONDITION_FAILED,
                    "Rental product is not eligible for return.",
                    resourceValue, "ReturnProduct");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void isProductWithinReturnPeriod(
            LocalDate deliveredOn,
            SoldProduct soldProduct,
            Long soldProductId,
            String orderNumber,
            ReturnType returnType) throws OperationError {
        if (ChronoUnit.DAYS.between(deliveredOn, LocalDate.now())
                >= soldProduct.getMaxDayForReturn()) {
            LOGGER.info("soldProduct : {} cannot return because max days for "
                    + "return is reached.", soldProductId);
            Map<String, Object> resourceValue = new HashMap<>();
            resourceValue.put("orderNumber", orderNumber);
            resourceValue.put("returnType", returnType);
            resourceValue.put("soldProductId", soldProductId);
            throw new OperationError(
                    HttpStatus.PRECONDITION_FAILED,
                    "Max days for returning a product is over.", resourceValue,
                    "ReturnProduct");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void isReturnRequestOpen(
            SoldProduct soldProduct,
            Long soldProductId,
            String orderNumber,
            ReturnType returnType) throws OperationError {
        if (soldProduct.getIsReturnRequestOpen()) {
            LOGGER.info("soldProduct : {} is already in open stage.",
                    soldProductId);
            Map<String, Object> resourceValue = new HashMap<>();
            resourceValue.put("orderNumber", orderNumber);
            resourceValue.put("returnType", returnType);
            resourceValue.put("soldProductId", soldProductId);
            throw new OperationError(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Request is already in open stage.", resourceValue,
                    "ReturnProduct");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void isProdutReturned(
            SoldProduct soldProduct,
            Long soldProductId,
            String orderNumber,
            ReturnType returnType) throws OperationError {
        if (soldProduct.getIsProductReturned()) {
            LOGGER.info("soldProduct : {} is already returned.", soldProductId);
            Map<String, Object> resourceValue = new HashMap<>();
            resourceValue.put("orderNumber", orderNumber);
            resourceValue.put("returnType", returnType);
            resourceValue.put("soldProductId", soldProductId);
            throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Product is already returned.", resourceValue,
                    "ReturnProduct");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private LocalDate isOrderDelivered(
            SoldProduct soldProduct,
            String orderNumber,
            ReturnType returnType,
            Long soldProductId) throws OperationError {
        OrderStatus orderStatus = OrderStatus.
                values()[orderDao.getOrderStatusByOrderId(
                        soldProduct.getOrderNumber())];
        LocalDate deliveredOn = orderDao.
                getDeliveredDateByOrderId(soldProduct.getOrderNumber());
        if (orderStatus != OrderStatus.DELIVERED) {
            LOGGER.info("Product is not able to return because "
                    + "order : {} is on stage : {}", orderNumber, orderStatus);
            Map<String, Object> resourceValue = new HashMap<>();
            resourceValue.put("orderNumber", orderNumber);
            resourceValue.put("returnType", returnType);
            resourceValue.put("soldProductId", soldProductId);
            throw new OperationError(
                    HttpStatus.PRECONDITION_FAILED,
                    "Product is in " + orderStatus + " stage.", resourceValue,
                    "ReturnProduct");
        }
        return deliveredOn;
    }

    @Transactional(rollbackFor = Exception.class)
    private void isProductMapWithOrder(
            SoldProduct soldProduct,
            String orderNumber,
            Long soldProductId,
            ReturnType returnType) throws OperationError {
        if (!soldProduct.getOrderNumber().equals(orderNumber)) {
            LOGGER.info("Order is not mapped with for soldProductId: {},"
                    + " returnType : {}, orderNumber: {}",
                    soldProductId, returnType, orderNumber);
            Map<String, Object> resourceValue = new HashMap<>();
            resourceValue.put("orderNumber", orderNumber);
            resourceValue.put("returnType", returnType);
            resourceValue.put("soldProductId", soldProductId);
            throw new OperationError(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Order id is not mapped with sold product.", resourceValue,
                    "ReturnProduct");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void isProductExist(
            SoldProduct soldProduct,
            Long soldProductId) throws ResourceNotFoundException {
        if (soldProduct == null) {
            throw new ResourceNotFoundException(
                    "soldProduct",
                    soldProductId,
                    "Requested sold product is not found");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void setReturnRequest(ReturnRequest returnRequest,
            Long soldProductId, ReturnType returnType,
            Double refundAmount, String customerId) {
        returnRequest.setSoldProduct(new SoldProduct(soldProductId));
        returnRequest.setRequestedOn(LocalDate.now());
        returnRequest.setReturnType(returnType);
        returnRequest.setRefundAmount(refundAmount);
        returnRequest.setCustomerId(customerId);
        returnRequest.setReturnStatus(ReturnStatus.ON_PROCESS);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse listReturnRequest(
            ReturnRequestPayload returnRequestPayload) {
        List<ReturnRequest> returnRequestList = returnProductRepository.
                listReturnRequest(returnRequestPayload);
        if (returnRequestList.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            return new ServiceResponse(HttpStatus.OK.value(), returnRequestList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelReturnRequest(String returnRequestId) {
        Boolean isReturnRequestUpdated = returnRequestDao.updateReturnRequest(
                UUID.fromString(returnRequestId), ReturnStatus.CANCELLED) != 0;
        LOGGER.info("ReturnRequest Status Result :: {}", isReturnRequestUpdated);
        Long soldProductId = returnRequestRepository.
                getSoldProductByReturnRequest(returnRequestId);
        Boolean lockStatus = rentalProductDao.
                updateLockStatusBySPID(Boolean.FALSE,
                        "",
                        soldProductId) > 0;
        LOGGER.info("LockStatus Result :: {}", lockStatus);
        Boolean soldProductStatus = soldProductDao.updateReturnRequestOpenStatus(
                soldProductId, false) > 0;
        return lockStatus && isReturnRequestUpdated && soldProductStatus;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateReturnStatus(String returnRequestId,
            ReturnStatus returnStatus, Boolean updateQuantity) {
        if (null == returnStatus) {
            return false;
        } else {
            switch (returnStatus) {
                case RETURNED:
                    return markReturnRequestAsReturned(
                            returnRequestId,
                            updateQuantity,
                            returnStatus);
                case CANCELLED:
                    return cancelReturnRequest(returnRequestId);
                default:
                    return returnRequestDao.updateReturnRequest(
                            UUID.fromString(returnRequestId), returnStatus) != 0;
            }
        }
    }

    private Boolean markReturnRequestAsReturned(
            String returnRequestId,
            Boolean updateQuantity,
            ReturnStatus returnStatus) throws NumberFormatException {
        Long soldProductId = returnRequestRepository.
                getSoldProductByReturnRequest(returnRequestId);
        soldProductDao.updateReturnRequestOpenStatus(soldProductId, false);
        soldProductDao.updateProductReturnStatus(soldProductId, true);
        Map<String, Object> respone = soldProductRepo.
                getProductAndStockIdBySoldProduct(soldProductId);
        LOGGER.info("returnReq: {}", soldProductId);
        productService.updateStock(
                Long.parseLong(respone.get("stockItemId").toString()),
                respone.get("productId").toString(), false,
                updateQuantity);
        Boolean isStatusUpdated = returnRequestDao.updateReturnRequest(
                UUID.fromString(returnRequestId), returnStatus) != 0;
        return isStatusUpdated;
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateAmountPayStatus(String returnRequestId,
            Boolean isAmountPaid) {
        ServiceResponse serviceResponse = new ServiceResponse();
        Boolean isStatusUpdated = returnRequestDao.updatePaymentStatus(
                UUID.fromString(returnRequestId), isAmountPaid) > 0;
        serviceResponse.setResponse(isStatusUpdated);
        serviceResponse.setStatusCode(HttpStatus.OK.value());
        return serviceResponse;
    }
}
