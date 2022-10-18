/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

import java.time.LocalDate;

/**
 *
 * @author z0043uwn
 */
public class StockItem {

    private Long stockItemId;
    private Long vendorId;
    private Double purchasedPrice;
    private LocalDate purchasedOn;

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(Double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public LocalDate getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(LocalDate purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

    @Override
    public String toString() {
        return "StockItem{" + "stockItemId=" + stockItemId + ", vendorId=" + vendorId + ", purchasedPrice=" + purchasedPrice + ", purchasedOn=" + purchasedOn + '}';
    }

}
