/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller;

import com.userservice.customer.model.State;
import com.userservice.model.ServiceResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface StateService {

        public ResponseEntity<ServiceResponse> addState(@Valid State state);

        public ResponseEntity<ServiceResponse> listAllStates();

        public ResponseEntity<ServiceResponse> deleteState(@NotNull @Positive Long stateId);

        public ResponseEntity<ServiceResponse> updateState(@Valid State state);

        public ResponseEntity<ServiceResponse> getStateById(@NotNull @Positive Long stateId);
}
