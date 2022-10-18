/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller;

import com.catalog.model.Category;
import com.catalog.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface CategoryController {

    public ResponseEntity<ServiceResponse> addCategory(@Valid Category category);

    public ResponseEntity<ServiceResponse> listAllCategories();

    public ResponseEntity<ServiceResponse> deleteCategory(@Positive Long categoryId);

    public ResponseEntity<ServiceResponse> updateCategory(@Valid Category category);

    public ResponseEntity<ServiceResponse> getCategoryById(@Positive Long categoryId);

}
