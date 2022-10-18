/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.impl;

import com.catalog.model.Product;
import com.catalog.model.ServiceResponse;
import com.catalog.requestpayload.ProductAttributePayload;
import com.catalog.controller.mgr.ProductControllerMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.catalog.controller.ProductController;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/product")
@Validated
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductControllerMgr productServiceMgr;

    @PostMapping("/v1/add-product")
    @Override
    public ResponseEntity<ServiceResponse> addProduct(@Valid @RequestBody Product product) {
        String productId = productServiceMgr.addProduct(product);
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(HttpStatus.OK.value());
        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("productId", productId);
        serviceResponse.setResponse(response);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @PutMapping("/v1/update-product")
    @Override
    public ResponseEntity<ServiceResponse> updateProduct(@Valid @RequestBody Product product) {
        ServiceResponse serviceResponse = productServiceMgr.updateProductDetails(product);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @DeleteMapping("/v1/delete-product")
    @Override
    public ResponseEntity<ServiceResponse> deleteProduct(@RequestParam String productId) {
        ServiceResponse serviceResponse = productServiceMgr.deleteProduct(productId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @PutMapping("/v1/deduct-stock")
    @Override
    public ResponseEntity<ServiceResponse> deductStock(@RequestParam @Positive Integer quantity,
            @RequestParam String productId) {
        ServiceResponse serviceResponse = productServiceMgr.deductStock(productId, quantity);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @PostMapping("/v1/list-product")
    @Override
    public ResponseEntity<ServiceResponse> listProducts(
            @RequestBody @Valid ProductAttributePayload productAttributePayload) {
        ServiceResponse serviceResponse = productServiceMgr.listProductsForClient(productAttributePayload);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @PostMapping("/admin/v1/list-product")
    @Override
    public ResponseEntity<ServiceResponse> listProductsForAdmin(
            @RequestBody @Valid ProductAttributePayload productAttributePayload) {
        ServiceResponse serviceResponse = productServiceMgr.listProductsForAdmin(productAttributePayload);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @GetMapping("/admin/v1/list-product/{productId}")
    @Override
    public ResponseEntity<ServiceResponse> getProductDetailsById(
            @PathVariable("productId") String productId) {
        Product product = productServiceMgr.getProductDetailsById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ServiceResponse(HttpStatus.OK.value(), "No product is available"));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ServiceResponse(HttpStatus.OK.value(), product));
        }
    }

    @GetMapping("/v1/product-quantity-validation")
    @Override
    public ResponseEntity<ServiceResponse> productQuantityValidation(@RequestParam String productId,
            @RequestParam @Positive Integer quantity) {
        Boolean isQuantityAvailable = productServiceMgr.isProductQuantityAvailable(productId, quantity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ServiceResponse(HttpStatus.OK.value(), isQuantityAvailable));
    }

    @PatchMapping("/v1/reserve-quantity")
    @Override
    public ServiceResponse reserveQuantity(@RequestBody @NotEmpty Map<String, Integer> productList) {
        Boolean isOperationExecuted = productServiceMgr.reserveQuantity(productList);
        return new ServiceResponse(HttpStatus.OK.value(), isOperationExecuted);
    }

    @PatchMapping("/v1/release-quantity")
    @Override
    public ServiceResponse releaseQuantity(@RequestBody @NotEmpty Map<String, Integer> productList) {
        Boolean isOperationExecuted = productServiceMgr.releaseQuantity(productList);
        return new ServiceResponse(HttpStatus.OK.value(), isOperationExecuted);
    }

}
