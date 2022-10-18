/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.cron;

import static com.userservice.constants.UserServiceConstants.TIME_INTERVAL_FOR_DELETE_OTP;
import com.userservice.customer.repository.EmailVerificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Priyank Agrawal
 */
@Component
public class DeleteUnusedOTP {

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteUnusedOTP.class);

    @Scheduled(fixedDelay = 120000)
    public Boolean deleteUnusedOTP() {
        boolean isOTPDeleted = emailVerificationRepository.deleteUnusedOTPs(TIME_INTERVAL_FOR_DELETE_OTP);
        LOGGER.info("Is OTP deleted: {}", isOTPDeleted);
        return isOTPDeleted;
    }
}
