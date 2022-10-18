/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.impl;

import com.userservice.customer.controller.CustomerSupportService;
import com.userservice.customer.controller.mgr.CustomerSupportControllerMgr;
import com.userservice.customer.model.CustomerQuery;
import com.userservice.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Priyank Agrawal
 */
@RequestMapping("/customer-support")
@RestController
@Validated
public class CustomerSupportServiceImpl implements CustomerSupportService {

        @Autowired
        private CustomerSupportControllerMgr supportControllerMgr;

        @PostMapping("/v1/capture-customer-query")
        @Override
        public ResponseEntity<ServiceResponse> captureCustomerQuery(@Valid @RequestBody CustomerQuery customerQuery) {
                ServiceResponse serviceResponse = supportControllerMgr.captureCustomerQuery(customerQuery);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-customer-queries")
        @Override
        public ResponseEntity<ServiceResponse> listCustomerQueries() {
                ServiceResponse serviceResponse = supportControllerMgr.listCustomerQueries();
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-customer-queries/{queryId}")
        @Override
        public ResponseEntity<ServiceResponse> listCustomerQueriesById(
                        @NotNull @Positive @PathVariable("queryId") Long queryId) {
                ServiceResponse serviceResponse = supportControllerMgr.listCustomerQueriesById(queryId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }
}
