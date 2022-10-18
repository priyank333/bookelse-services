/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.controller.impl;

import com.gateway.controller.AppController;
import com.gateway.dto.AdminAppLogin;
import com.gateway.dto.CustomerAppLogin;
import com.gateway.dto.TokenResponse;
import com.gateway.service.AuthService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author z0043uwn
 */
@RestController
@RequestMapping("/app")
@Validated
public class AppControllerImpl implements AppController {

    @Autowired
    private AuthService authService;

    @Override
    @PostMapping(value = "/customer/v1/login")
    public ResponseEntity<TokenResponse> getTokenForCustomer(
            @Valid @RequestBody CustomerAppLogin customerAppLogin) {
        customerAppLogin.setEmailId(customerAppLogin.getEmailId().toUpperCase());
        TokenResponse tokenResponse = authService.customerAuthentication(
                customerAppLogin.getEmailId(), customerAppLogin.getPassword());
        return ResponseEntity.status(tokenResponse.getResponse()).
                body(tokenResponse);
    }

    @Override
    @PostMapping(value = "/admin/v1/login")
    public ResponseEntity<TokenResponse> getTokenForAdmin(
            @Valid @RequestBody AdminAppLogin adminAppLogin) {
        TokenResponse tokenResponse = authService.adminAuthentication(
                adminAppLogin.getUsername(), adminAppLogin.getPassword());
        return ResponseEntity.status(tokenResponse.getResponse()).
                body(tokenResponse);
    }

}
