/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller;

import com.userservice.customer.model.Customer;
import com.userservice.customer.model.CustomerBankAccount;
import com.userservice.customer.requestpayload.BankAccRegPayload;
import com.userservice.customer.requestpayload.CustomerRequestPayload;
import com.userservice.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Priyank Agrawal
 */
public interface CustomerService {

        public ResponseEntity<ServiceResponse> registerCustomer(@Valid Customer customer);

        public ResponseEntity<ServiceResponse> listCustomers(CustomerRequestPayload customerRequestPayload);

        public ResponseEntity<ServiceResponse> getCustomerById(@NotNull String customerId);

        public ResponseEntity<ServiceResponse> getCustomerBankDetailById(@NotNull String customerId);

        public ResponseEntity<ServiceResponse> verifyEmail(@NotBlank String verificationCode, String customerId);

        public ResponseEntity<ServiceResponse> resetCustomerPassword(@NotBlank String verificationCode,
                        @NotBlank @Email @RequestParam("emailId") String emailId,
                        @NotBlank @RequestParam("password") String password);

        public ResponseEntity<ServiceResponse> getEmailAddressByCustomer(@NotNull String customerId);

        public ResponseEntity<ServiceResponse> updateUserBankDetails(@NotNull String customerId,
                        @Valid CustomerBankAccount customerBankAccount, @NotBlank String otp);

        public ResponseEntity<ServiceResponse> isAccountActive(@NotNull String customerId);

        public ResponseEntity<ServiceResponse> addCustomerBankDetails(@Valid BankAccRegPayload accRegPayload);

        public ResponseEntity<ServiceResponse> hasBankAccount(@NotNull String customerId);

        public ResponseEntity<ServiceResponse> updateUserDetails(@Valid @RequestBody Customer customer);

}
