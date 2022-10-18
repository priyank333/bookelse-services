/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.impl;

import com.userservice.customer.model.State;
import com.userservice.customer.controller.StateService;
import com.userservice.customer.controller.mgr.StateControllerMgr;
import com.userservice.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/state")
@RestController
@Validated
public class StateServiceImpl implements StateService {

        @Autowired
        private StateControllerMgr stateServiceMgr;

        @PostMapping("/v1/add-state")
        @Override
        public ResponseEntity<ServiceResponse> addState(@Valid @RequestBody State state) {
                ServiceResponse serviceResponse = stateServiceMgr.addState(state);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-state")
        @Override
        public ResponseEntity<ServiceResponse> listAllStates() {
                ServiceResponse serviceResponse = stateServiceMgr.listAllStates();
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @DeleteMapping("/v1/delete-state")
        @Override
        public ResponseEntity<ServiceResponse> deleteState(@NotNull @Positive @RequestParam Long stateId) {
                ServiceResponse serviceResponse = stateServiceMgr.deleteState(stateId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PutMapping("/v1/update-state")
        @Override
        public ResponseEntity<ServiceResponse> updateState(@Valid @RequestBody State state) {
                ServiceResponse serviceResponse = stateServiceMgr.updateState(state);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-state/{stateId}")
        @Override
        public ResponseEntity<ServiceResponse> getStateById(@NotNull @Positive @PathVariable("stateId") Long stateId) {
                ServiceResponse serviceResponse = stateServiceMgr.getStateById(stateId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

}
