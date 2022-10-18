/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.impl;

import com.coms.controller.mgr.OrderControllerMgr;
import com.coms.dto.OnlinePaymentOrderDetails;
import com.coms.model.OrderStatus;
import com.coms.dto.ServiceResponse;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.coms.controller.OrderController;
import com.coms.payload.CustomerOrderForAdminPayload;
import com.coms.payload.CustomerOrderPayload;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author z0043uwn
 */
@RestController
@RequestMapping("/order")
@Validated
public class OrderControllerImpl implements OrderController {

    @Autowired
    private OrderControllerMgr orderServiceMgr;
    private static final Logger LOGGER = LoggerFactory.getLogger(
            OrderControllerImpl.class);

    @PostMapping("/v1/create-cash-order")
    @Override
    public ResponseEntity createOrderForCash(
            @NotNull @Positive @RequestParam Long draftId,
            @NotNull @RequestParam String customerId,
            @NotNull @RequestParam String billingAddress,
            @NotNull @RequestParam String shippingAddress) {
        ServiceResponse serviceResponse = new ServiceResponse();
        Map<String, String> response = new LinkedHashMap<>();
        String orderNumber = orderServiceMgr.processOrderForCash(
                customerId,
                draftId,
                billingAddress,
                shippingAddress);
        if (orderNumber != null || !"".equals(orderNumber)) {
            serviceResponse.setStatusCode(HttpStatus.CREATED.value());
            response.put("customerOrderNumber", orderNumber);
            serviceResponse.setResponse(response);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{orderNumber}").buildAndExpand(response.
                    get("customerOrderNumber")).toUri();
            return ResponseEntity.created(location).body(serviceResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ServiceResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Issue with generating order number"));
        }
    }

    @PostMapping("/v1/create-electronic-payment-order")
    @Override
    public ResponseEntity createOrderForOnlinePayment(
            @NotNull @Positive @RequestParam Long draftId,
            @NotNull @RequestParam String customerId,
            @NotNull @RequestParam String billingAddress,
            @NotNull @RequestParam String shippingAddress) {
        ServiceResponse serviceResponse = new ServiceResponse();
        OnlinePaymentOrderDetails onlinePaymentOrderDetails
                = orderServiceMgr.
                        createOrderForOnlinePayment(
                                customerId,
                                draftId,
                                billingAddress,
                                shippingAddress);
        if (onlinePaymentOrderDetails != null) {
            serviceResponse.setStatusCode(HttpStatus.CREATED.value());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{orderNumber}").buildAndExpand(
                    onlinePaymentOrderDetails.getOrderNumber()).toUri();
            serviceResponse.setResponse(onlinePaymentOrderDetails);
            return ResponseEntity.created(location).body(
                    serviceResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ServiceResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Issue with generating order number"));
        }
    }

    @Override
    @PatchMapping("/v1/update-amount-paid")
    public ResponseEntity<ServiceResponse> updateAmountPaid(
            @NotNull @NotBlank @RequestParam String orderNumber,
            @NotNull @RequestParam Boolean isAmountPaid) {
        Boolean result = orderServiceMgr.updatePaymentStatus(orderNumber,
                isAmountPaid);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), result));
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(
                    new ServiceResponse(
                            HttpStatus.PRECONDITION_FAILED.value(),
                            "Invalid orderNumber"));
        }
    }

    @Override
    @PatchMapping("/v1/update-order-status")
    public ResponseEntity<ServiceResponse> updateOrderStatus(
            @NotBlank @RequestParam String orderNumber,
            @NotNull @RequestParam OrderStatus orderStatus) {
        Boolean result = orderServiceMgr.updateOrderStatus(
                orderNumber, orderStatus);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), result));
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(
                    new ServiceResponse(
                            HttpStatus.PRECONDITION_FAILED.value(),
                            "Invalid orderNumber"));
        }
    }

    @PostMapping("/v1/list-customer-order")
    @Override
    public ResponseEntity<ServiceResponse> listCustomerOrders(
            @Valid @RequestBody CustomerOrderPayload orderPayload) {
        ServiceResponse serviceResponse = orderServiceMgr.
                listCustomerOrders(orderPayload);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }

    @PostMapping("/admin/v1/list-customer-order")
    @Override
    public ResponseEntity<ServiceResponse> listCustomerOrders(
            @Valid @RequestBody CustomerOrderForAdminPayload orderPayload) {
        ServiceResponse serviceResponse = orderServiceMgr.
                listCustomerOrders(orderPayload);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }

    @PostMapping("/test")
    public void test(@RequestBody Object object) {
        LOGGER.info("Payload , {}",object);
    }
}
