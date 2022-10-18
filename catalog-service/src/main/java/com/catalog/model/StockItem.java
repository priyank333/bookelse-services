/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class StockItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(updatable = false)
    private Long stockItemId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH, orphanRemoval = false)
    @JoinColumn(name = "productId", nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Product product;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH, orphanRemoval = false)
    @JoinColumn(name = "vendorId", nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Vendor vendor;
    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isProductSold;
    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isProductActive;
    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isProductReturnedToVendor;
    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double purchasedPrice;
    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate purchasedOn;

    public StockItem() {
    }

    public StockItem(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Boolean getIsProductSold() {
        return isProductSold;
    }

    public void setIsProductSold(Boolean isProductSold) {
        this.isProductSold = isProductSold;
    }

    public Boolean getIsProductActive() {
        return isProductActive;
    }

    public void setIsProductActive(Boolean isProductActive) {
        this.isProductActive = isProductActive;
    }

    public Boolean getIsProductReturnedToVendor() {
        return isProductReturnedToVendor;
    }

    public void setIsProductReturnedToVendor(Boolean isProductReturnedToVendor) {
        this.isProductReturnedToVendor = isProductReturnedToVendor;
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
        return "StockItem{" + "stockItemId=" + stockItemId + ", product=" + product + ", vendor=" + vendor
                + ", isProductSold=" + isProductSold + ", isProductActive=" + isProductActive
                + ", isProductReturnedToVendor=" + isProductReturnedToVendor + ", purchasedPrice=" + purchasedPrice
                + ", purchasedOn=" + purchasedOn + '}';
    }

}
