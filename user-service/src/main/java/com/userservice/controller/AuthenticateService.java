/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.controller;

import com.userservice.model.ServiceResponse;

import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface AuthenticateService {

    public ResponseEntity<ServiceResponse> customerAuthentication(String username, String password);

    public ResponseEntity<ServiceResponse> adminAuthentication(String username, String password);

}
