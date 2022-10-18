/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author z0043uwn
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class RentalProduct implements Serializable {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(updatable = false)
    private String rentalProductId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderNumber", nullable = false)
    private CustomerOrder customerOrder;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "soldProductId", nullable = false)
    private SoldProduct soldProduct;
    private Integer initialReturnPeriod;
    private Integer rentPeriod;
    private Integer extendedRentPeriod;
    private Double extendedRentalCharge;
    private Boolean isPeriodExtended;
    private Boolean isEligibleForReturn;
    private Double delayCharge;
    private LocalDate dueDate;
    private Double refundAmount;
    private Double deposite;
    private Double depreciation;
    private Double rentalCharge;
    private LocalDate rentedOn;
    private LocalDate extendedOn;
    private Boolean isLocked;
    private String lockReason;

    public RentalProduct() {
    }

    public RentalProduct(String rentalProductId) {
        this.rentalProductId = rentalProductId;
    }

    public String getRentalProductId() {
        return rentalProductId;
    }

    public void setRentalProductId(String rentalProductId) {
        this.rentalProductId = rentalProductId;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public SoldProduct getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(SoldProduct soldProduct) {
        this.soldProduct = soldProduct;
    }

    public Integer getInitialReturnPeriod() {
        return initialReturnPeriod;
    }

    public void setInitialReturnPeriod(Integer initialReturnPeriod) {
        this.initialReturnPeriod = initialReturnPeriod;
    }

    public Integer getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(Integer rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public Integer getExtendedRentPeriod() {
        return extendedRentPeriod;
    }

    public void setExtendedRentPeriod(Integer extendedRentPeriod) {
        this.extendedRentPeriod = extendedRentPeriod;
    }

    public Double getExtendedRentalCharge() {
        return extendedRentalCharge;
    }

    public void setExtendedRentalCharge(Double extendedRentalCharge) {
        this.extendedRentalCharge = extendedRentalCharge;
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

    public Double getDelayCharge() {
        return delayCharge;
    }

    public void setDelayCharge(Double delayCharge) {
        this.delayCharge = delayCharge;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public Double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Double depreciation) {
        this.depreciation = depreciation;
    }

    public Double getRentalCharge() {
        return rentalCharge;
    }

    public void setRentalCharge(Double rentalCharge) {
        this.rentalCharge = rentalCharge;
    }

    public LocalDate getRentedOn() {
        return rentedOn;
    }

    public void setRentedOn(LocalDate rentedOn) {
        this.rentedOn = rentedOn;
    }

    public LocalDate getExtendedOn() {
        return extendedOn;
    }

    public void setExtendedOn(LocalDate extendedOn) {
        this.extendedOn = extendedOn;
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

    @Override
    public String toString() {
        return "RentalProduct{" + "rentalProductId=" + rentalProductId + ", customerOrder=" + customerOrder + ", soldProduct=" + soldProduct + ", initialReturnPeriod=" + initialReturnPeriod + ", rentPeriod=" + rentPeriod + ", extendedRentPeriod=" + extendedRentPeriod + ", extendedRentalCharge=" + extendedRentalCharge + ", isPeriodExtended=" + isPeriodExtended + ", isEligibleForReturn=" + isEligibleForReturn + ", delayCharge=" + delayCharge + ", dueDate=" + dueDate + ", refundAmount=" + refundAmount + ", deposite=" + deposite + ", depreciation=" + depreciation + ", rentalCharge=" + rentalCharge + ", rentedOn=" + rentedOn + ", extendedOn=" + extendedOn + ", isLocked=" + isLocked + ", lockReason=" + lockReason + '}';
    }
}
