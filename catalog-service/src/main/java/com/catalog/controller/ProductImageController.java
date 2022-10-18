/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller;

import com.catalog.model.ServiceResponse;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Priyank Agrawal
 */
public interface ProductImageController {

        public ResponseEntity<ServiceResponse> uploadImageInS3(String productId, @NotBlank String productName,
                        @NotEmpty List<MultipartFile> productImage);

        public ResponseEntity<ServiceResponse> deleteImage(@Positive Long imageId);

        public ResponseEntity<ServiceResponse> listImagesByProduct(String productId);
}
