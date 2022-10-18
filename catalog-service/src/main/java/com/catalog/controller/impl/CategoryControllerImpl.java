/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.impl;

import com.catalog.model.Category;
import com.catalog.model.ServiceResponse;
import com.catalog.controller.mgr.CategoryControllerMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.catalog.controller.CategoryController;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/category")
@Validated
public class CategoryControllerImpl implements CategoryController {

        @Autowired
        private CategoryControllerMgr categoryServiceMgr;

        @PostMapping("/v1/add-category")
        @Override
        public ResponseEntity<ServiceResponse> addCategory(@Valid @RequestBody Category category) {
                ServiceResponse serviceResponse = categoryServiceMgr.addCategory(category);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-category")
        @Override
        public ResponseEntity<ServiceResponse> listAllCategories() {
                ServiceResponse serviceResponse = categoryServiceMgr.listAllCategories();
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @DeleteMapping("/v1/delete-category")
        @Override
        public ResponseEntity<ServiceResponse> deleteCategory(@Positive @RequestParam Long categoryId) {
                ServiceResponse serviceResponse = categoryServiceMgr.deleteCategory(categoryId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PutMapping("/v1/update-category")
        @Override
        public ResponseEntity<ServiceResponse> updateCategory(@Valid @RequestBody Category category) {
                ServiceResponse serviceResponse = categoryServiceMgr.updateCategory(category);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-category/{categoryId}")
        @Override
        public ResponseEntity<ServiceResponse> getCategoryById(@Positive @PathVariable Long categoryId) {
                ServiceResponse serviceResponse = categoryServiceMgr.getCategoryById(categoryId);
                return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
        }
}