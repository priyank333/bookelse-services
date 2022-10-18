/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.impl;

import com.userservice.customer.model.Customer;
import com.userservice.customer.controller.CustomerService;
import com.userservice.customer.model.OTPFor;
import com.userservice.customer.controller.mgr.CustomerControllerMgr;
import com.userservice.customer.controller.mgr.VerificationControllerMgr;
import com.userservice.model.ServiceResponse;
import com.userservice.customer.model.CustomerBankAccount;
import com.userservice.customer.requestpayload.BankAccRegPayload;
import com.userservice.customer.requestpayload.CustomerRequestPayload;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Priyank Agrawal
 */
@RequestMapping("/customer")
@RestController
@Validated
public class CustomerServiceImpl implements CustomerService {

        @Autowired
        private CustomerControllerMgr customerServiceMgr;

        @Autowired
        private VerificationControllerMgr verificationMgr;

        @PostMapping("/v1/register")
        @Override
        public ResponseEntity<ServiceResponse> registerCustomer(@Valid @RequestBody Customer customer) {

                ServiceResponse serviceResponse = customerServiceMgr.registerCustomer(customer);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/admin/v1/list-customer")
        @Override
        public ResponseEntity<ServiceResponse> listCustomers(
                        @RequestBody CustomerRequestPayload customerRequestPayload) {
                ServiceResponse serviceResponse = customerServiceMgr.listCustomers(customerRequestPayload);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-customer/{customerId}")
        @Override
        public ResponseEntity<ServiceResponse> getCustomerById(@NotNull @PathVariable String customerId) {
                ServiceResponse serviceResponse = customerServiceMgr.getCustomerById(customerId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-customer-bank-details/{customerId}")
        @Override
        public ResponseEntity<ServiceResponse> getCustomerBankDetailById(@NotNull @PathVariable String customerId) {
                ServiceResponse serviceResponse = customerServiceMgr
                                .listCustomers(new CustomerRequestPayload(customerId));
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/email-verification")
        @Override
        public ResponseEntity<ServiceResponse> verifyEmail(@NotBlank @RequestParam String verificationCode,
                        @RequestParam String customerId) {
                ServiceResponse serviceResponse = customerServiceMgr.verifyCustomerMailAddress(verificationCode,
                                customerId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @Override
        @PostMapping("/v1/reset-password")
        public ResponseEntity<ServiceResponse> resetCustomerPassword(@NotBlank @RequestParam String verificationCode,
                        @NotBlank @Email @RequestParam("emailId") String emailId,
                        @NotBlank @RequestParam("password") String password) {
                ServiceResponse serviceResponse = customerServiceMgr.resetPassword(verificationCode, emailId, password);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/get-email-address-by-customer")
        @Override
        public ResponseEntity<ServiceResponse> getEmailAddressByCustomer(@NotNull @RequestParam String customerId) {
                ServiceResponse serviceResponse = customerServiceMgr.getEmailAddressByCustomer(customerId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/update-bank-details")
        @Override
        public ResponseEntity<ServiceResponse> updateUserBankDetails(@NotNull @RequestParam String customerId,
                        @Valid @RequestBody CustomerBankAccount customerBankAccount,
                        @NotBlank @RequestParam String otp) {
                ServiceResponse serviceResponse;
                Boolean isUserAuthenticated = verificationMgr.otpVerification(customerId, otp,
                                OTPFor.UPDATE_BANK_DETAILS);
                if (isUserAuthenticated) {
                        serviceResponse = customerServiceMgr.updateCustomerBankAccountDetails(customerBankAccount,
                                        customerId);
                } else {
                        serviceResponse = new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid OTP");
                }
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @Override
        @GetMapping("/v1/is-account-active")
        public ResponseEntity<ServiceResponse> isAccountActive(@NotNull @RequestParam String customerId) {
                ServiceResponse serviceResponse = customerServiceMgr.isAccountActive(customerId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/add-bank-details")
        @Override
        public ResponseEntity<ServiceResponse> addCustomerBankDetails(
                        @RequestBody @Valid BankAccRegPayload accRegPayload) {
                ServiceResponse serviceResponse = customerServiceMgr.addBankDetails(accRegPayload);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/has-bank-account")
        @Override
        public ResponseEntity<ServiceResponse> hasBankAccount(@NotNull @RequestParam String customerId) {
                ServiceResponse serviceResponse = customerServiceMgr.hasCustomerRegisteredBank(customerId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PutMapping("/v1/update-account-details")
        @Override
        public ResponseEntity<ServiceResponse> updateUserDetails(@Valid @RequestBody Customer customer) {
                ServiceResponse serviceResponse = customerServiceMgr.updateUserDetails(customer);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

}
