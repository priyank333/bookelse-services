/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.userservice.customer.model.OTPFor;
import com.userservice.customer.repository.EmailVerificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class VerificationControllerMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationControllerMgr.class);

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    public Boolean otpVerification(String customerId, String otp, OTPFor oTPFor) {
        String otpInDB = emailVerificationRepository.getVerificationCode(customerId, oTPFor);
        if (otp.equals(otpInDB)) {
            LOGGER.info("OTP is sucessfully verified for customerId : {}", customerId);
            emailVerificationRepository.deleteOTPByCustomerAndType(customerId, oTPFor);
            return true;
        } else {
            LOGGER.info("OTP is not verified for customerId : {}", customerId);
            return false;
        }
    }
}
