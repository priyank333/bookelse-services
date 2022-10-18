/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.impl;

import com.userservice.customer.controller.AddressService;
import com.userservice.customer.controller.mgr.AddressControllerMgr;
import com.userservice.customer.model.Address;
import com.userservice.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Priyank Agrawal
 */
@RequestMapping("/customer-address")
@RestController
@Validated
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressControllerMgr addressControllerMgr;

    @PostMapping("/v1/add")
    @Override
    public ResponseEntity<ServiceResponse> addAddress(@Valid @RequestBody Address address) {
        ServiceResponse serviceResponse = addressControllerMgr.addAddress(address);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @PostMapping("/v1/update")
    @Override
    public ResponseEntity<ServiceResponse> updateAddress(@Valid @RequestBody Address address) {
        ServiceResponse serviceResponse = addressControllerMgr.updateAddress(address);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @GetMapping("/v1/list-address")
    @Override
    public ResponseEntity<ServiceResponse> listByCustomer(
            @RequestParam("customerId") String customerId) {
        ServiceResponse serviceResponse = addressControllerMgr.findByCustomer(customerId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @GetMapping("/v1/list-address/{addressId}")
    @Override
    public ResponseEntity<ServiceResponse> listByAddressId(@NotNull @PathVariable Long addressId) {
        ServiceResponse serviceResponse = addressControllerMgr.findById(addressId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @DeleteMapping("/v1/delete")
    @Override
    public ResponseEntity<ServiceResponse> deleteAddress(
            @RequestParam("addressId") Long addressId) {
        ServiceResponse serviceResponse = addressControllerMgr.deleteAddress(addressId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

}
