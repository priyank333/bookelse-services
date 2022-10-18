/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller;

import com.catalog.model.ServiceResponse;
import com.catalog.model.University;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface UniversityController {

    public ResponseEntity<ServiceResponse> addUniversity(@Valid University university);

    public ResponseEntity<ServiceResponse> listAllUniversities();

    public ResponseEntity<ServiceResponse> deleteUniversity(@Positive Long universityId);

    public ResponseEntity<ServiceResponse> updateUniversity(@Valid University university);

    public ResponseEntity<ServiceResponse> getUniversityById(@Positive Long universityId);
}
