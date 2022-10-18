/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller;

import com.userservice.customer.model.City;
import com.userservice.model.ServiceResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface CityService {

    public ResponseEntity<ServiceResponse> addCity(@Valid City city);

    public ResponseEntity<ServiceResponse> listCity();

    public ResponseEntity<ServiceResponse> deleteCity(@NotNull @Positive Long cityId);

    public ResponseEntity<ServiceResponse> updateCity(@Valid City city);

    public ResponseEntity<ServiceResponse> getCitiesByStateId(@NotNull @Positive Long stateId);

    public ResponseEntity<ServiceResponse> getCityByCityId(@NotNull @Positive Long cityId);
}
