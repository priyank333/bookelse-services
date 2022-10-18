/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 *
 * @author z0043uwn
 */
public class RentalProductDTO {

    private String orderNumber;
    private String customerId;
    private Timestamp orderedDate;
    private Long soldProductId;
    private Double discount;
    private String productId;
    private String productName;
    private String remarks;
    private Double sellPrice;
    private String rentalProductId;
    private Double delayCharge;
    private Double depreciation;
    private Timestamp dueDate;
    private Integer initialReturnPeriod;
    private Double refundAmount;
    private Double deposite;
    private Double rentalCharge;
    private Integer rentPeriod;
    private Boolean isPeriodExtended;
    private Boolean isEligibleForReturn;
    private LocalDate extendedOn;
    private LocalDate rentedOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long stockItemId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long purchasedFrom;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate purchasedOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double purchasedPrice;
    private Boolean isLocked;
    private String lockReason;
    private Integer extendedRentalPeriod;
    private Double extendedRentalCharge;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer dueDays;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Timestamp orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Long getSoldProductId() {
        return soldProductId;
    }

    public void setSoldProductId(Long soldProductId) {
        this.soldProductId = soldProductId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getRentalProductId() {
        return rentalProductId;
    }

    public void setRentalProductId(String rentalProductId) {
        this.rentalProductId = rentalProductId;
    }

    public Double getDelayCharge() {
        return delayCharge;
    }

    public void setDelayCharge(Double delayCharge) {
        this.delayCharge = delayCharge;
    }

    public Double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Double depreciation) {
        this.depreciation = depreciation;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getInitialReturnPeriod() {
        return initialReturnPeriod;
    }

    public void setInitialReturnPeriod(Integer initialReturnPeriod) {
        this.initialReturnPeriod = initialReturnPeriod;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getDeposite() {
        return deposite;
    }

    public void setDeposite(Double deposite) {
        this.deposite = deposite;
    }

    public Double getRentalCharge() {
        return rentalCharge;
    }

    public void setRentalCharge(Double rentalCharge) {
        this.rentalCharge = rentalCharge;
    }

    public Integer getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(Integer rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public Boolean getIsPeriodExtended() {
        return isPeriodExtended;
    }

    public void setIsPeriodExtended(Boolean isPeriodExtended) {
        this.isPeriodExtended = isPeriodExtended;
    }

    public Boolean getIsEligibleForReturn() {
        return isEligibleForReturn;
    }

    public void setIsEligibleForReturn(Boolean isEligibleForReturn) {
        this.isEligibleForReturn = isEligibleForReturn;
    }

    public LocalDate getExtendedOn() {
        return extendedOn;
    }

    public void setExtendedOn(LocalDate extendedOn) {
        this.extendedOn = extendedOn;
    }

    public LocalDate getRentedOn() {
        return rentedOn;
    }

    public void setRentedOn(LocalDate rentedOn) {
        this.rentedOn = rentedOn;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    public Long getPurchasedFrom() {
        return purchasedFrom;
    }

    public void setPurchasedFrom(Long purchasedFrom) {
        this.purchasedFrom = purchasedFrom;
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

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public Integer getExtendedRentalPeriod() {
        return extendedRentalPeriod;
    }

    public void setExtendedRentalPeriod(Integer extendedRentalPeriod) {
        this.extendedRentalPeriod = extendedRentalPeriod;
    }

    public Double getExtendedRentalCharge() {
        return extendedRentalCharge;
    }

    public void setExtendedRentalCharge(Double extendedRentalCharge) {
        this.extendedRentalCharge = extendedRentalCharge;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(Integer dueDays) {
        this.dueDays = dueDays;
    }

    @Override
    public String toString() {
        return "RentalProductDTO{" + "orderNumber=" + orderNumber + ", customerId=" + customerId + ", orderedDate=" + orderedDate + ", soldProductId=" + soldProductId + ", discount=" + discount + ", productId=" + productId + ", productName=" + productName + ", remarks=" + remarks + ", sellPrice=" + sellPrice + ", rentalProductId=" + rentalProductId + ", delayCharge=" + delayCharge + ", depreciation=" + depreciation + ", dueDate=" + dueDate + ", initialReturnPeriod=" + initialReturnPeriod + ", refundAmount=" + refundAmount + ", deposite=" + deposite + ", rentalCharge=" + rentalCharge + ", rentPeriod=" + rentPeriod + ", isPeriodExtended=" + isPeriodExtended + ", isEligibleForReturn=" + isEligibleForReturn + ", extendedOn=" + extendedOn + ", rentedOn=" + rentedOn + ", stockItemId=" + stockItemId + ", purchasedFrom=" + purchasedFrom + ", purchasedOn=" + purchasedOn + ", purchasedPrice=" + purchasedPrice + ", isLocked=" + isLocked + ", lockReason=" + lockReason + ", extendedRentalPeriod=" + extendedRentalPeriod + ", extendedRentalCharge=" + extendedRentalCharge + ", dueDays=" + dueDays + '}';
    }

}
