/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller;

import com.coms.dto.ServiceResponse;
import com.coms.model.ReturnStatus;
import com.coms.model.ReturnType;
import com.coms.payload.ReturnRequestPayload;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author z0043uwn
 */
public interface ReturnRequestController {

    public ResponseEntity createReturnRequest(
            @NotNull @Positive Long soldProductId,
            @NotNull ReturnType returnType,
            @NotNull @NotBlank String orderNumber);

    public ResponseEntity cancelReturnRequest(
            @NotNull @NotBlank String returnRequestId);

    public ResponseEntity updateReturnRequestStatus(
            @NotNull @NotBlank String returnRequestId,
            @NotNull ReturnStatus returnStatus,
            @NotNull Boolean updateQuantity);

    public ResponseEntity listReturnRequests(
            @Valid ReturnRequestPayload returnRequestPayload);

    public ResponseEntity<ServiceResponse> listReturnRequestsForAdmin(
            @Valid @RequestBody ReturnRequestPayload returnRequestPayload);

    public ResponseEntity<ServiceResponse> updateCustomerPaymentStatus(
            @NotNull @NotBlank @RequestParam String returnRequestId,
            @NotNull @RequestParam Boolean isAmountPaid);
}
