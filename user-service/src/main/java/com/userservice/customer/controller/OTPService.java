/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller;

import com.userservice.customer.model.OTPFor;
import com.userservice.model.ServiceResponse;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface OTPService {

    public ResponseEntity<ServiceResponse> sendOTP(
            @NotBlank @Email String emailId,
            @NotNull OTPFor oTPFor);
}
