/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller;

import com.userservice.customer.model.Area;
import com.userservice.model.ServiceResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface AreaService {

    public ResponseEntity<ServiceResponse> addArea(@Valid Area area);

    public ResponseEntity<ServiceResponse> updateArea(@Valid Area area);

    public ResponseEntity<ServiceResponse> getAreaById(@NotNull @Positive Long areaId);

    public ResponseEntity<ServiceResponse> isPincodeExist(@NotBlank String pincode);

    public ResponseEntity<ServiceResponse> deleteArea(@NotNull @Positive Long areaId);

    public ResponseEntity<ServiceResponse> getAreasByCityId(@NotNull @Positive Long cityId);

    public ResponseEntity<ServiceResponse> listAllAreas();
}
