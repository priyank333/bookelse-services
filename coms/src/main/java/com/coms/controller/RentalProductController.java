/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller;

import com.coms.dto.ServiceResponse;
import com.coms.payload.RentalProductForAdminPayload;
import com.coms.payload.RentalProductPayload;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author z0043uwn
 */
public interface RentalProductController {

    public ResponseEntity<ServiceResponse> listRentalProducts(
            @Valid RentalProductPayload rentalProductPayload);

    public ResponseEntity<ServiceResponse> listRentalProducts(
           @Valid RentalProductForAdminPayload rentalProductPayload);

    public ResponseEntity<ServiceResponse> extendPeriod(
            @NotNull @NotBlank String rentalProductId,
            @NotNull @Positive Integer period);
}
