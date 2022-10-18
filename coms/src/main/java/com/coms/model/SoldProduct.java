/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author z0043uwn
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
@DynamicInsert
public class SoldProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial",updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long soldProductId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long stockItemId;
    private String productName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "productPrice")
    private Double sellPrice;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double discount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remarks;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isProductOnRent;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxDayForReturn;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double depreciation;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String orderNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate purchasedOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double purchasedPrice;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long purchasedFrom;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isReturnRequestOpen;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isProductReturned;

    public SoldProduct() {
    }

    public SoldProduct(Long soldProductId) {
        this.soldProductId = soldProductId;
    }

    public Long getSoldProductId() {
        return soldProductId;
    }

    public void setSoldProductId(Long soldProductId) {
        this.soldProductId = soldProductId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsProductOnRent() {
        return isProductOnRent;
    }

    public void setIsProductOnRent(Boolean isProductOnRent) {
        this.isProductOnRent = isProductOnRent;
    }

    public Integer getMaxDayForReturn() {
        return maxDayForReturn;
    }

    public void setMaxDayForReturn(Integer maxDayForReturn) {
        this.maxDayForReturn = maxDayForReturn;
    }

    public Double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Double depreciation) {
        this.depreciation = depreciation;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(LocalDate purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

    public Double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(Double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public Long getPurchasedFrom() {
        return purchasedFrom;
    }

    public void setPurchasedFrom(Long purchasedFrom) {
        this.purchasedFrom = purchasedFrom;
    }

    public Boolean getIsReturnRequestOpen() {
        return isReturnRequestOpen;
    }

    public void setIsReturnRequestOpen(Boolean isReturnRequestOpen) {
        this.isReturnRequestOpen = isReturnRequestOpen;
    }

    public Boolean getIsProductReturned() {
        return isProductReturned;
    }

    public void setIsProductReturned(Boolean isProductReturned) {
        this.isProductReturned = isProductReturned;
    }

    @Override
    public String toString() {
        return "SoldProduct{" + "soldProductId=" + soldProductId + ", productId=" + productId + ", stockItemId=" + stockItemId + ", productName=" + productName + ", sellPrice=" + sellPrice + ", discount=" + discount + ", remarks=" + remarks + ", quantity=" + quantity + ", isProductOnRent=" + isProductOnRent + ", maxDayForReturn=" + maxDayForReturn + ", depreciation=" + depreciation + ", orderNumber=" + orderNumber + ", purchasedOn=" + purchasedOn + ", purchasedPrice=" + purchasedPrice + ", purchasedFrom=" + purchasedFrom + ", isReturnRequestOpen=" + isReturnRequestOpen + ", isProductReturned=" + isProductReturned + '}';
    }
    
    
}
