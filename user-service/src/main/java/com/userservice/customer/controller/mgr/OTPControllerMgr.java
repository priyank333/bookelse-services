/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.userservice.customer.dao.CustomerDao;
import com.userservice.customer.dao.CustomerEmailVerificationDao;
import com.userservice.customer.model.Customer;
import com.userservice.customer.model.CustomerEmailVerification;
import com.userservice.customer.model.OTPFor;
import com.userservice.customer.services.MailService;
import com.userservice.model.ServiceResponse;
import com.userservice.util.UserServiceUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class OTPControllerMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPControllerMgr.class);
    @Autowired
    private MailService mailService;

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerEmailVerificationDao emailVerificationDao;

    @Qualifier("OTPMailDescription")
    @Autowired
    public Map<OTPFor, Map<String, String>> mailDescription;

    public ServiceResponse sendOTP(String customerEmailAddress, OTPFor oTPFor) {
        ServiceResponse serviceResponse = new ServiceResponse();
        Boolean isAccActive = customerDao.isAccountActiveByEmail(customerEmailAddress);
        Boolean isMailSent;
        if (isAccActive != null && isAccActive) {
            Map<String, String> mailBody = mailDescription.get(oTPFor);
            if (mailBody == null) {
                LOGGER.error("No mail body found");
            }
            LOGGER.info("mail body: {}", mailDescription);
            String customerId = customerDao.findCustomerIdByEmail(customerEmailAddress);
            String firstName = customerDao.findFirstNameByEmailId(customerEmailAddress);
            String otp = new UserServiceUtil().generateOTP();
            CustomerEmailVerification cev = emailVerificationDao
                    .save(new CustomerEmailVerification(otp, new Customer(customerId), oTPFor));
            if (cev != null) {
                LOGGER.info("OTP is stored in db with id: {}", cev.getCustomerEmailVerificationId());
            } else {
                LOGGER.error("OTP is not stored in DB");
            }
            isMailSent = mailService.sendOTPInMail(firstName, customerEmailAddress, mailBody, otp);
            if (isMailSent) {
                serviceResponse.setStatusCode(HttpStatus.OK.value());
                serviceResponse.setResponse(isMailSent);
            } else {
                serviceResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                serviceResponse.setResponse("Issue with mailing service");
            }
        } else {
            LOGGER.info("User is not found to send otp for : {}", oTPFor);
            serviceResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
        }
        return serviceResponse;
    }
}
