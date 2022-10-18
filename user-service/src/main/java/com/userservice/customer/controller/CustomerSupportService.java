/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller;

import com.userservice.customer.model.CustomerQuery;
import com.userservice.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Priyank Agarwal
 */
public interface CustomerSupportService {

        public ResponseEntity<ServiceResponse> captureCustomerQuery(@Valid @RequestBody CustomerQuery customerQuery);

        public ResponseEntity<ServiceResponse> listCustomerQueries();

        public ResponseEntity<ServiceResponse> listCustomerQueriesById(
                        @NotNull @Positive @PathVariable("queryId") Long queryId);
}
