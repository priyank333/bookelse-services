/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.impl;

import com.userservice.customer.controller.OTPService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.userservice.customer.model.OTPFor;
import com.userservice.customer.controller.mgr.OTPControllerMgr;
import com.userservice.model.ServiceResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/otp-service")
@Validated
public class OTPServiceImpl implements OTPService {

    @Autowired
    private OTPControllerMgr oTPServiceMgr;

    @Override
    @PostMapping("/v1/send-otp-in-mail")
    public ResponseEntity<ServiceResponse> sendOTP(@NotBlank @Email @RequestParam("emailId") String emailId,
            @NotNull @RequestParam("otpFor") OTPFor oTPFor) {
        ServiceResponse serviceResponse = oTPServiceMgr.sendOTP(emailId, oTPFor);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }
}
