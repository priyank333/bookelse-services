/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller;

import com.catalog.model.ServiceResponse;
import com.catalog.model.Vendor;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface VendorController {

    public ResponseEntity<ServiceResponse> addVendor(@Valid Vendor vendor);

    public ResponseEntity<ServiceResponse> listAllVendors();

    public ResponseEntity<ServiceResponse> deleteVendor(@Positive Long vendorId);

    public ResponseEntity<ServiceResponse> updateVendor(@Valid Vendor vendor);

    public ResponseEntity<ServiceResponse> getVendorById(@Positive Long vendorId);
}
