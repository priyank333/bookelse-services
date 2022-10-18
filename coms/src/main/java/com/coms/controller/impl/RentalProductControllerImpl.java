/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.impl;

import com.coms.controller.RentalProductController;
import com.coms.controller.mgr.RentalProductControllerMgr;
import com.coms.dto.ServiceResponse;
import com.coms.payload.RentalProductForAdminPayload;
import com.coms.payload.RentalProductPayload;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author z0043uwn
 */
@RestController
@RequestMapping("/rental-product")
@Validated
public class RentalProductControllerImpl implements RentalProductController {

    @Autowired
    private RentalProductControllerMgr rentalProductControllerMgr;

    @PostMapping("/v1/list-rental-product")
    @Override
    public ResponseEntity<ServiceResponse> listRentalProducts(
            @Valid @RequestBody RentalProductPayload rentalProductPayload) {
        ServiceResponse serviceResponse = rentalProductControllerMgr.
                listRentalProducts(rentalProductPayload);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }

    @PostMapping("/admin/v1/list-rental-product")
    @Override
    public ResponseEntity<ServiceResponse> listRentalProducts(
            @Valid @RequestBody RentalProductForAdminPayload rentalProductPayload) {
        ServiceResponse serviceResponse = rentalProductControllerMgr.
                listRentalProducts(rentalProductPayload);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }

    @PostMapping("/v1/extend-period")
    @Override
    public ResponseEntity<ServiceResponse> extendPeriod(
            @NotNull @NotBlank @RequestParam String rentalProductId,
            @NotNull @Positive @RequestParam Integer period) {
        ServiceResponse serviceResponse = rentalProductControllerMgr.
                extendPeriod(rentalProductId, period);
        return ResponseEntity.status(serviceResponse.getStatusCode()).
                body(serviceResponse);
    }
}
