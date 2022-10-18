/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class ReturnRequest implements Serializable {

    @Id
    @GeneratedValue
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(updatable = false)
    private UUID returnRequestId;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "soldProductId")
    private SoldProduct soldProduct;
    private Double refundAmount;
    private LocalDate requestedOn;
    private ReturnType returnType;
    private ReturnStatus returnStatus;
    private Boolean isAmountPaidToCustomer;
    private String customerId;

    public UUID getReturnRequestId() {
        return returnRequestId;
    }

    public void setReturnRequestId(UUID returnRequestId) {
        this.returnRequestId = returnRequestId;
    }

    public SoldProduct getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(SoldProduct soldProduct) {
        this.soldProduct = soldProduct;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public LocalDate getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(LocalDate requestedOn) {
        this.requestedOn = requestedOn;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }

    public ReturnStatus getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Boolean getIsAmountPaidToCustomer() {
        return isAmountPaidToCustomer;
    }

    public void setIsAmountPaidToCustomer(Boolean isAmountPaidToCustomer) {
        this.isAmountPaidToCustomer = isAmountPaidToCustomer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ReturnRequest{" + "returnRequestId=" + returnRequestId + ", soldProduct=" + soldProduct + ", refundAmount=" + refundAmount + ", requestedOn=" + requestedOn + ", returnType=" + returnType + ", returnStatus=" + returnStatus + ", isAmountPaidToCustomer=" + isAmountPaidToCustomer + ", customerId=" + customerId + '}';
    }

}
