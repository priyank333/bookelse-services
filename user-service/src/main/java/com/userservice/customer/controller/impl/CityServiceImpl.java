/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.impl;

import com.userservice.customer.model.City;
import com.userservice.customer.controller.CityService;
import com.userservice.customer.controller.mgr.CityControllerMgr;
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
@RequestMapping("/city")
@RestController
@Validated
public class CityServiceImpl implements CityService {

    @Autowired
    private CityControllerMgr cityServiceMgr;

    @PostMapping("/v1/add-city")
    @Override
    public ResponseEntity<ServiceResponse> addCity(
            @Valid @RequestBody City city) {
        ServiceResponse serviceResponse = cityServiceMgr.addCity(city);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(
                serviceResponse);
    }

    @GetMapping("/v1/list-city")
    @Override
    public ResponseEntity<ServiceResponse> listCity() {
        ServiceResponse serviceResponse = cityServiceMgr.listAllCities();
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(
                serviceResponse);
    }

    @DeleteMapping("/v1/delete-city")
    @Override
    public ResponseEntity<ServiceResponse> deleteCity(
            @NotNull @Positive @RequestParam Long cityId) {
        ServiceResponse serviceResponse = cityServiceMgr.deleteCity(cityId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(
                serviceResponse);
    }

    @PutMapping("/v1/update-city")
    @Override
    public ResponseEntity<ServiceResponse> updateCity(
            @Valid @RequestBody City city) {
        ServiceResponse serviceResponse = cityServiceMgr.updateCity(city);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(
                serviceResponse);
    }

    @GetMapping("/v1/list-cities/state/{stateId}")
    @Override
    public ResponseEntity<ServiceResponse> getCitiesByStateId(
            @NotNull @Positive @PathVariable("stateId") Long stateId) {
        ServiceResponse serviceResponse = cityServiceMgr.getCityByState(stateId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(
                serviceResponse);
    }

    @GetMapping("/v1/list-city/{cityId}")
    @Override
    public ResponseEntity<ServiceResponse> getCityByCityId(
            @NotNull @Positive @PathVariable("cityId") Long cityId) {
        ServiceResponse serviceResponse = cityServiceMgr.getCityById(cityId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(
                serviceResponse);
    }
}
