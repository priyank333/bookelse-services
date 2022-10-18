/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.impl;

import com.catalog.model.ServiceResponse;
import com.catalog.controller.mgr.DraftControllerMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.catalog.controller.DraftController;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/draft")
@Validated
public class DraftControllerImpl implements DraftController {

    @Autowired
    private DraftControllerMgr draftServiceMgr;

    @PutMapping("/v1/update-draft")
    @Override
    public synchronized ResponseEntity<ServiceResponse> updateProductsInDraft(@RequestParam String customerId,
            @RequestParam String productId, @RequestParam @PositiveOrZero Integer quantity) {
        ServiceResponse serviceResponse = draftServiceMgr.updateProductsInDraft(customerId, productId, quantity);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @GetMapping("/v1/get-draft")
    @Override
    public ResponseEntity<ServiceResponse> getDraft(@RequestParam(required = false) String customerId,
            @Positive @RequestParam(required = false) Long draftId) {
        ServiceResponse serviceResponse = draftServiceMgr.getDraft(customerId, draftId);
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }

    @DeleteMapping("/v1/delete-draft")
    @Override
    public ResponseEntity<ServiceResponse> deleteDraft(@Positive @RequestParam(required = false) Long draftId,
            @RequestParam(required = false) String customerId) {
        ServiceResponse serviceResponse;
        if (customerId != null) {
            serviceResponse = draftServiceMgr.deleteDraftByCustomer(customerId);
        } else if (draftId != null) {
            serviceResponse = draftServiceMgr.deleteDraft(draftId);
        } else {
            serviceResponse = new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "Service expect either customerId or draftId");
        }
        return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
    }
}
