/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.impl;

import com.catalog.model.ServiceResponse;
import com.catalog.model.Vendor;
import com.catalog.controller.mgr.VendorControllerMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.catalog.controller.VendorController;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/vendor")
@Validated
public class VendorControllerImpl implements VendorController {

    @Autowired
    private VendorControllerMgr vendorServiceMgr;

    @PostMapping("/v1/add-vendor")
    @Override
    public ResponseEntity<ServiceResponse> addVendor(@Valid @RequestBody Vendor vendor) {
        ServiceResponse serviceResponse = vendorServiceMgr.addVendor(vendor);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @GetMapping("/v1/list-vendor")
    @Override
    public ResponseEntity<ServiceResponse> listAllVendors() {
        ServiceResponse serviceResponse = vendorServiceMgr.listAllVendors();
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @DeleteMapping("/v1/delete-vendor")
    @Override
    public ResponseEntity<ServiceResponse> deleteVendor(@Positive @RequestParam Long vendorId) {
        ServiceResponse serviceResponse = vendorServiceMgr.deleteVendor(vendorId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @PutMapping("/v1/update-vendor")
    @Override
    public ResponseEntity<ServiceResponse> updateVendor(@RequestBody @Valid Vendor vendor) {
        ServiceResponse serviceResponse = vendorServiceMgr.updateVendor(vendor);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @GetMapping("/v1/list-vendor/{vendorId}")
    @Override
    public ResponseEntity<ServiceResponse> getVendorById(@PathVariable("vendorId") @Positive Long vendorId) {
        ServiceResponse serviceResponse = vendorServiceMgr.getVendorById(vendorId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

}
