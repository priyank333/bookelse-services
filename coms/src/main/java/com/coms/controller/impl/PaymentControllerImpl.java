/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.impl;

import com.coms.controller.mgr.PaymentControllerMgr;
import com.coms.dto.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coms.controller.PaymentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author z0043uwn
 */
@RestController
@RequestMapping("/payment")
@Validated
public class PaymentControllerImpl implements PaymentController {

    @Autowired
    private PaymentControllerMgr paymentServiceMgr;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            PaymentControllerImpl.class);

    @PostMapping("/v1/update-payment-status")
    @Override
    public ResponseEntity<ServiceResponse> updatePgaymentStatus(
            @RequestHeader("X-Razorpay-Signature") String razorpaySignature,
            HttpEntity<String> httpEntity) {
        LOGGER.info("Received Input :: {}", httpEntity.getBody());
        ServiceResponse serviceResponse = paymentServiceMgr.
                processPayment(httpEntity.getBody(), razorpaySignature);
        return ResponseEntity.status(
                serviceResponse.getStatusCode()).body(serviceResponse);
    }

}
