/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.impl;

import com.userservice.customer.model.Area;
import com.userservice.customer.controller.AreaService;
import com.userservice.customer.controller.mgr.AreaControllerMgr;
import com.userservice.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
@RequestMapping("/area")
@RestController
@Validated
public class AreaServiceImpl implements AreaService {

        @Autowired
        private AreaControllerMgr areaServiceMgr;

        @PostMapping("/v1/add-area")
        @Override
        public ResponseEntity<ServiceResponse> addArea(@Valid @RequestBody Area area) {
                ServiceResponse serviceResponse = areaServiceMgr.addArea(area);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PutMapping("/v1/update-area")
        @Override
        public ResponseEntity<ServiceResponse> updateArea(@Valid @RequestBody Area area) {
                ServiceResponse serviceResponse = areaServiceMgr.updateArea(area);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-area/{areaId}")
        @Override
        public ResponseEntity<ServiceResponse> getAreaById(@NotNull @Positive @PathVariable Long areaId) {
                ServiceResponse serviceResponse = areaServiceMgr.getAreaById(areaId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/is-pincode-exist")
        @Override
        public ResponseEntity<ServiceResponse> isPincodeExist(@NotBlank @RequestParam String pincode) {
                ServiceResponse serviceResponse = areaServiceMgr.isPincodeExist(pincode);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @DeleteMapping("/v1/delete-area")
        @Override
        public ResponseEntity<ServiceResponse> deleteArea(@NotNull @Positive @RequestParam Long areaId) {
                ServiceResponse serviceResponse = areaServiceMgr.deleteArea(areaId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-area/city/{cityId}")
        @Override
        public ResponseEntity<ServiceResponse> getAreasByCityId(@NotNull @Positive @PathVariable Long cityId) {
                ServiceResponse serviceResponse = areaServiceMgr.getAreasByCity(cityId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-area")
        @Override
        public ResponseEntity<ServiceResponse> listAllAreas() {
                ServiceResponse serviceResponse = areaServiceMgr.listAllAreas();
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }
}
