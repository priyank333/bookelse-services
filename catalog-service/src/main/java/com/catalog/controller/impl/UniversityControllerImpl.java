/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.impl;

import com.catalog.model.ServiceResponse;
import com.catalog.model.University;
import com.catalog.controller.mgr.UniversityControllerMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.catalog.controller.UniversityController;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/university")
@Validated
public class UniversityControllerImpl implements UniversityController {

        @Autowired
        private UniversityControllerMgr serviceMgr;

        @PostMapping("/v1/add-university")
        @Override
        public ResponseEntity<ServiceResponse> addUniversity(@Valid @RequestBody University university) {
                ServiceResponse serviceResponse = serviceMgr.addUniversity(university);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-university")
        @Override
        public ResponseEntity<ServiceResponse> listAllUniversities() {
                ServiceResponse serviceResponse = serviceMgr.listAllUniversities();
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @DeleteMapping("/v1/delete-university")
        @Override
        public ResponseEntity<ServiceResponse> deleteUniversity(@Positive @RequestParam Long universityId) {
                ServiceResponse serviceResponse = serviceMgr.deleteUniversity(universityId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PutMapping("/v1/update-university")
        @Override
        public ResponseEntity<ServiceResponse> updateUniversity(@Valid @RequestBody University university) {
                ServiceResponse serviceResponse = serviceMgr.updateUniversity(university);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-university/{universityId}")
        @Override
        public ResponseEntity<ServiceResponse> getUniversityById(
                        @PathVariable("universityId") @Positive Long universityId) {
                ServiceResponse serviceResponse = serviceMgr.getUniversityById(universityId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

}
