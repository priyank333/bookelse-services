/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.services.mgr;

import com.userservice.admin.dao.AdminDao;
import com.userservice.customer.dao.CustomerDao;
import com.userservice.model.ServiceResponse;
import com.userservice.model.UserInfo;
import com.userservice.util.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class UserAuthenticationServiceMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationServiceMgr.class);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AdminDao adminDao;

    public ServiceResponse validateCustomerCredential(String emailId, String password) {
        password = new Hash().getHashValue(password);
        String customerId = customerDao.getCustomerIdByEmailIdAndPassword(emailId, password);
        if (customerId != null) {
            LOGGER.info("User with emailId : {}", emailId, " is authenticated");
            return new ServiceResponse(HttpStatus.OK.value(), new UserInfo("CUSTOMER", customerId));
        } else {
            LOGGER.info("User with emailId : {}", emailId, " is not authenticated");
            return new ServiceResponse(HttpStatus.UNAUTHORIZED.value(), new UserInfo("UNAUTHORIZED"));
        }
    }

    public ServiceResponse validateAdminCredential(String username, String password) {
        password = new Hash().getHashValue(password);
        String adminId = adminDao.getIdByUsernameAndPassword(username, password);
        if (adminId != null) {
            LOGGER.info("User with userId : {}", username, " is authenticated");
            return new ServiceResponse(HttpStatus.OK.value(), new UserInfo("ADMIN", adminId));
        } else {
            LOGGER.info("User with userId : {}", username, " is not authenticated");
            return new ServiceResponse(HttpStatus.UNAUTHORIZED.value(), new UserInfo("UNAUTHORIZED"));
        }
    }
}
