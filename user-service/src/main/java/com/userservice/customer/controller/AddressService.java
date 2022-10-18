/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller;

import com.userservice.customer.model.Address;
import com.userservice.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface AddressService {

    public ResponseEntity<ServiceResponse> addAddress(@Valid Address address);

    public ResponseEntity<ServiceResponse> updateAddress(@Valid Address address);

    public ResponseEntity<ServiceResponse> listByCustomer(@NotNull String customerId);

    public ResponseEntity<ServiceResponse> listByAddressId(@NotNull Long addressId);

    public ResponseEntity<ServiceResponse> deleteAddress(@NotNull Long addressId);

}
