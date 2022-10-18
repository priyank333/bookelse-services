/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller;

import com.coms.model.OrderStatus;
import com.coms.dto.ServiceResponse;
import com.coms.payload.CustomerOrderForAdminPayload;
import com.coms.payload.CustomerOrderPayload;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author z0043uwn
 */
public interface OrderController {

    public ResponseEntity createOrderForCash(
            @NotNull @Positive Long draftId,
            @NotNull String customerId,
            @NotNull String billingAddress,
            @NotNull String shippingAddress);

    public ResponseEntity createOrderForOnlinePayment(
            @NotNull @Positive Long draftId,
            @NotNull String customerId,
            @NotNull String billingAddress,
            @NotNull String shippingAddress);

    public ResponseEntity<ServiceResponse> updateAmountPaid(
            @NotNull @NotBlank @RequestParam String orderNumber,
            @NotNull @RequestParam Boolean isAmountPaid);

    public ResponseEntity<ServiceResponse> updateOrderStatus(
            @NotBlank @RequestParam String orderNumber,
            @NotNull @RequestParam OrderStatus orderStatus);

    public ResponseEntity<ServiceResponse> listCustomerOrders(
            @Valid CustomerOrderPayload orderPayload);

    public ResponseEntity<ServiceResponse> listCustomerOrders(
            @Valid CustomerOrderForAdminPayload orderPayload);
}
