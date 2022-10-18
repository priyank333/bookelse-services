/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller;

import com.catalog.model.ServiceResponse;
import com.catalog.model.StockItem;
import com.catalog.requestpayload.StockItemViewPayload;
import com.catalog.requestpayload.StockPayload;
import com.catalog.requestpayload.UpdateProductSoldPayload;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface StockController {

        public ResponseEntity<ServiceResponse> addStock(@Valid StockPayload stockPayload);

        public ResponseEntity<ServiceResponse> updateStock(@Valid StockItem stockItem);

        public ResponseEntity<ServiceResponse> deleteStockItem(@Positive Long stockItemId);

        public ResponseEntity<ServiceResponse> updateIsProductSold(@Valid UpdateProductSoldPayload productSoldPayload);

        public ResponseEntity<ServiceResponse> listStockItems(@Valid StockItemViewPayload itemViewPayload);

        public ResponseEntity<ServiceResponse> makeStockActive(@Positive Long stockItemId);

        public ResponseEntity<ServiceResponse> makeStockInactive(@Positive Long stockItemId);

        public ResponseEntity<ServiceResponse> returnStockToVendor(@Positive Long stockItemId);

        public ResponseEntity<ServiceResponse> getReturnedStockFromVendor(@Positive Long stockItemId);
}
