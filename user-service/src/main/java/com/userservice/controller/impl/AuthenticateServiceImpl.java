/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.controller.impl;

import com.userservice.model.ServiceResponse;
import com.userservice.services.mgr.UserAuthenticationServiceMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.userservice.controller.AuthenticateService;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticateServiceImpl implements AuthenticateService {

        @Autowired
        private UserAuthenticationServiceMgr authenticationServiceMgr;

        @PostMapping("/customer")
        @Override
        public ResponseEntity<ServiceResponse> customerAuthentication(@RequestParam String emailId,
                        @RequestParam String password) {
                ServiceResponse serviceResponse = authenticationServiceMgr.validateCustomerCredential(emailId,
                                password);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/admin")
        @Override
        public ResponseEntity<ServiceResponse> adminAuthentication(@RequestParam String username,
                        @RequestParam String password) {
                ServiceResponse serviceResponse = authenticationServiceMgr.validateAdminCredential(username, password);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }
}
