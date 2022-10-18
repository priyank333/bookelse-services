/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.controller;

import com.gateway.dto.AdminAppLogin;
import com.gateway.dto.CustomerAppLogin;
import com.gateway.dto.TokenResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Priyank Agrawal
 */
public interface AppController {

    public ResponseEntity<TokenResponse> getTokenForCustomer(
            @Valid @RequestBody CustomerAppLogin customerAppLogin);

    public ResponseEntity<TokenResponse> getTokenForAdmin(
            @Valid @RequestBody AdminAppLogin adminAppLogin);
    
}
