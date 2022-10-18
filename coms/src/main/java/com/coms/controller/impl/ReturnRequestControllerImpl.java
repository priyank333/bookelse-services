/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.impl;

import com.coms.controller.mgr.ReturnableProductControllerMgr;
import com.coms.model.ReturnStatus;
import com.coms.model.ReturnType;
import com.coms.dto.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.coms.payload.ReturnRequestPayload;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import com.coms.controller.ReturnRequestController;
import org.springframework.web.bind.annotation.PatchMapping;

/**
 *
 * @author z0043uwn
 */
@RestController
@RequestMapping("/return-request")
@Validated
public class ReturnRequestControllerImpl implements ReturnRequestController {

    @Autowired
    private ReturnableProductControllerMgr returnableProductServiceMgr;

    @Override
    @PostMapping("/v1/create-return-request")
    public ResponseEntity createReturnRequest(
            @NotNull @Positive @RequestParam Long soldProductId,
            @NotNull @RequestParam ReturnType returnType,
            @NotNull @NotBlank @RequestParam String orderNumber) {
        ServiceResponse serviceResponse = returnableProductServiceMgr.
                createReturnProductRequest(
                        soldProductId, returnType, orderNumber);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }

    @Override
    @PostMapping("/v1/cancel-return-request")
    public ResponseEntity cancelReturnRequest(
            @NotNull @NotBlank String returnRequestId) {
        Boolean result = returnableProductServiceMgr.
                cancelReturnRequest(returnRequestId);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), result));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ServiceResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), result));
        }
    }

    @Override
    @PatchMapping("/v1/update-return-request-status")
    public ResponseEntity updateReturnRequestStatus(
            @NotNull @NotBlank String returnRequestId,
            @NotNull @RequestParam ReturnStatus returnStatus,
            @NotNull @RequestParam Boolean updateQuantity) {
        Boolean result = returnableProductServiceMgr.
                updateReturnStatus(returnRequestId, returnStatus, updateQuantity);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ServiceResponse(HttpStatus.OK.value(), result));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ServiceResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(), result));
        }
    }

    @PostMapping("/v1/list-return-request")
    @Override
    public ResponseEntity<ServiceResponse> listReturnRequests(
            @Valid @RequestBody ReturnRequestPayload returnRequestPayload) {
        if (returnRequestPayload.customerId != null) {
            ServiceResponse serviceResponse = returnableProductServiceMgr.
                    listReturnRequest(returnRequestPayload);
            return ResponseEntity.status(serviceResponse.getStatusCode()).
                    body(serviceResponse);
        } else {
            return ResponseEntity.status(
                    HttpStatus.UNPROCESSABLE_ENTITY).
                    body(new ServiceResponse(
                            HttpStatus.UNPROCESSABLE_ENTITY.value(),
                            "Customer Id is require"));
        }
    }

    @PostMapping("/admin/v1/list-return-request")
    @Override
    public ResponseEntity<ServiceResponse> listReturnRequestsForAdmin(
            @Valid @RequestBody ReturnRequestPayload returnRequestPayload) {
        ServiceResponse serviceResponse = returnableProductServiceMgr.
                listReturnRequest(returnRequestPayload);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }

    @PatchMapping("/v1/update-customer-payment-status")
    @Override
    public ResponseEntity<ServiceResponse> updateCustomerPaymentStatus(
            @NotNull @NotBlank @RequestParam String returnRequestId,
            @NotNull @RequestParam Boolean isAmountPaid) {
        ServiceResponse serviceResponse = returnableProductServiceMgr.
                updateAmountPayStatus(returnRequestId, isAmountPaid);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }
}
