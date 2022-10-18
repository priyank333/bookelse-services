/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller;

import com.coms.dto.ServiceResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author z0043uwn
 */
public interface PaymentController {

    public ResponseEntity<ServiceResponse> updatePgaymentStatus(
            @RequestHeader("X-Razorpay-Signature") String razorpaySignature,
            HttpEntity<String> httpEntity);

}
