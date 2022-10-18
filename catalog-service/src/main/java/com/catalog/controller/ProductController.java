/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller;

import com.catalog.model.Product;
import com.catalog.model.ServiceResponse;
import com.catalog.requestpayload.ProductAttributePayload;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Priyank Agrawal
 */
public interface ProductController {

    public ResponseEntity<ServiceResponse> addProduct(@Valid Product product);

    public ResponseEntity<ServiceResponse> updateProduct(@Valid Product product);

    public ResponseEntity<ServiceResponse> deleteProduct(String productId);

    public ResponseEntity<ServiceResponse> deductStock(@Positive Integer quantity, String productId);

    public ResponseEntity<ServiceResponse> getProductDetailsById(String productId);

    public ResponseEntity<ServiceResponse> productQuantityValidation(String productId, @Positive Integer quantity);

    public ServiceResponse reserveQuantity(@NotEmpty Map<String, Integer> productList);

    public ServiceResponse releaseQuantity(@NotEmpty Map<String, Integer> productList);

    public ResponseEntity<ServiceResponse> listProducts(@Valid ProductAttributePayload productAttributePayload);
    
    public ResponseEntity<ServiceResponse> listProductsForAdmin(
            @RequestBody @Valid ProductAttributePayload productAttributePayload);

}
