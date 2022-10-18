/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.impl;

import com.catalog.controller.ProductImageController;
import com.catalog.controller.mgr.ProductImageControllerMgr;
import com.catalog.model.ServiceResponse;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/product-image")
@Validated
public class ProductImageControllerImpl implements ProductImageController {

        @Autowired
        private ProductImageControllerMgr imageControllerMgr;

        @PostMapping("/v1/upload-image")
        @Override
        public ResponseEntity<ServiceResponse> uploadImageInS3(@RequestParam(required = true) String productId,
                        @RequestParam(required = true) @NotBlank String productName,
                        @NotEmpty List<MultipartFile> productImage) {
                ServiceResponse response = imageControllerMgr.uploadImageInS3(productId, productName, productImage);
                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        @DeleteMapping("/v1/delete-image")
        @Override
        public ResponseEntity<ServiceResponse> deleteImage(@RequestParam(required = true) @Positive Long imageId) {
                ServiceResponse serviceResponse = imageControllerMgr.deleteProductImage(imageId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @GetMapping("/v1/list-image/product/{productId}")
        @Override
        public ResponseEntity<ServiceResponse> listImagesByProduct(
                        @PathVariable("productId")String productId) {
                ServiceResponse serviceResponse = imageControllerMgr.listProductImagesByProduct(productId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

}
