/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.impl;

import com.catalog.controller.StockController;
import com.catalog.controller.mgr.StockControllerMgr;
import com.catalog.model.ServiceResponse;
import com.catalog.model.StockItem;
import com.catalog.requestpayload.StockItemViewPayload;
import com.catalog.requestpayload.StockPayload;
import com.catalog.requestpayload.UpdateProductSoldPayload;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/stock")
@Validated
public class StockControllerImpl implements StockController {

        @Autowired
        private StockControllerMgr stockControllerMgr;

        @PostMapping("/v1/add-stock")
        @Override
        public ResponseEntity<ServiceResponse> addStock(@Valid @RequestBody StockPayload stockPayload) {
                ServiceResponse serviceResponse = stockControllerMgr.addStock(stockPayload);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PutMapping("/v1/update-stock")
        @Override
        public ResponseEntity<ServiceResponse> updateStock(@Valid @RequestBody StockItem stockItem) {
                ServiceResponse serviceResponse = stockControllerMgr.updateStockItem(stockItem);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @DeleteMapping("/v1/delete-stock")
        @Override
        public ResponseEntity<ServiceResponse> deleteStockItem(@Positive @RequestParam Long stockItemId) {
                ServiceResponse serviceResponse = stockControllerMgr.deleteStockItem(stockItemId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PatchMapping("/v1/update-is-product-sold")
        @Override
        public ResponseEntity<ServiceResponse> updateIsProductSold(
                        @Valid @RequestBody UpdateProductSoldPayload productSoldPayload) {
                ServiceResponse serviceResponse = stockControllerMgr.updateIsProductSold(
                                productSoldPayload.stockItemList, productSoldPayload.isProductSold,
                                productSoldPayload.updateQuantity, productSoldPayload.productId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/list-stock-item")
        @Override
        public ResponseEntity<ServiceResponse> listStockItems(
                        @Valid @RequestBody StockItemViewPayload itemViewPayload) {
                ServiceResponse serviceResponse = stockControllerMgr.listStockItems(itemViewPayload);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/make-stock-active")
        @Override
        public ResponseEntity<ServiceResponse> makeStockActive(@Positive @RequestParam Long stockItemId) {
                ServiceResponse serviceResponse = stockControllerMgr.makeStockActive(stockItemId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/make-stock-inactive")
        @Override
        public ResponseEntity<ServiceResponse> makeStockInactive(@Positive @RequestParam Long stockItemId) {
                ServiceResponse serviceResponse = stockControllerMgr.makeStockInactive(stockItemId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/return-stock-to-vendor")
        @Override
        public ResponseEntity<ServiceResponse> returnStockToVendor(@Positive @RequestParam Long stockItemId) {
                ServiceResponse serviceResponse = stockControllerMgr.returnStockToVendor(stockItemId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }

        @PostMapping("/v1/get-returned-stock-from-vendor")
        @Override
        public ResponseEntity<ServiceResponse> getReturnedStockFromVendor(@Positive @RequestParam Long stockItemId) {
                ServiceResponse serviceResponse = stockControllerMgr.getReturnedStockFromVendor(stockItemId);
                return ResponseEntity.status(serviceResponse.getStatusCode()).body(serviceResponse);
        }
}
