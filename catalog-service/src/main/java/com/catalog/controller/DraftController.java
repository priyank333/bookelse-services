/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller;

import com.catalog.model.ServiceResponse;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface DraftController {

    public ResponseEntity<ServiceResponse> updateProductsInDraft(String customerId, String productId,
            @PositiveOrZero Integer quantity);

    public ResponseEntity<ServiceResponse> getDraft(String customerId, @Positive Long draftId);

    public ResponseEntity<ServiceResponse> deleteDraft(@Positive Long draftId, String customerId);

}
