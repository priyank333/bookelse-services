/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.model;

import com.coms.dto.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author z0043uwn
 */
@Entity
@Table(indexes = {
    @Index(name = "index_on_ordered_date",
            columnList = "orderedDate",
            unique = false)})
@DynamicUpdate
@DynamicInsert
public class CustomerOrder implements Serializable, Cloneable {

    @Id
    @Column(updatable = false)
    private String orderNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Column(nullable = true)
    private String paymentMode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private Timestamp orderedDate;
    private LocalDate deliveredOn;
    private LocalDate receivedOn;
    private LocalDate shippedOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = true)
    private Double finalAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private Boolean isAmountPaid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String customerId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderNumber")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SoldProduct> productsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = true)
    private String paymentOrderNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = true)
    private String paymentIdNo;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Customer customer;
    private Double shippingCharge;
    private PaymentStatus paymentStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentErrorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentErrorDescription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentErrorSource;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentErrorStep;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentErrorReason;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bankTransactionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rrn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String authCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String upiTransactionId;
    private String billingAddress;
    private String shippingAddress;

    public CustomerOrder() {
    }

    public CustomerOrder(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Timestamp getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Timestamp orderedDate) {
        this.orderedDate = orderedDate;
    }

    public LocalDate getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(LocalDate deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public LocalDate getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(LocalDate receivedOn) {
        this.receivedOn = receivedOn;
    }

    public LocalDate getShippedOn() {
        return shippedOn;
    }

    public void setShippedOn(LocalDate shippedOn) {
        this.shippedOn = shippedOn;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Boolean getIsAmountPaid() {
        return isAmountPaid;
    }

    public void setIsAmountPaid(Boolean isAmountPaid) {
        this.isAmountPaid = isAmountPaid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<SoldProduct> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<SoldProduct> productsList) {
        this.productsList = productsList;
    }

    public String getPaymentOrderNumber() {
        return paymentOrderNumber;
    }

    public void setPaymentOrderNumber(String paymentOrderNumber) {
        this.paymentOrderNumber = paymentOrderNumber;
    }

    public String getPaymentIdNo() {
        return paymentIdNo;
    }

    public void setPaymentIdNo(String paymentIdNo) {
        this.paymentIdNo = paymentIdNo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(Double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentErrorCode() {
        return paymentErrorCode;
    }

    public void setPaymentErrorCode(String paymentErrorCode) {
        this.paymentErrorCode = paymentErrorCode;
    }

    public String getPaymentErrorDescription() {
        return paymentErrorDescription;
    }

    public void setPaymentErrorDescription(String paymentErrorDescription) {
        this.paymentErrorDescription = paymentErrorDescription;
    }

    public String getPaymentErrorSource() {
        return paymentErrorSource;
    }

    public void setPaymentErrorSource(String paymentErrorSource) {
        this.paymentErrorSource = paymentErrorSource;
    }

    public String getPaymentErrorStep() {
        return paymentErrorStep;
    }

    public void setPaymentErrorStep(String paymentErrorStep) {
        this.paymentErrorStep = paymentErrorStep;
    }

    public String getPaymentErrorReason() {
        return paymentErrorReason;
    }

    public void setPaymentErrorReason(String paymentErrorReason) {
        this.paymentErrorReason = paymentErrorReason;
    }

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(String bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUpiTransactionId() {
        return upiTransactionId;
    }

    public void setUpiTransactionId(String upiTransactionId) {
        this.upiTransactionId = upiTransactionId;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" + "orderNumber=" + orderNumber + ", orderStatus=" + orderStatus + ", paymentMode=" + paymentMode + ", orderedDate=" + orderedDate + ", deliveredOn=" + deliveredOn + ", receivedOn=" + receivedOn + ", shippedOn=" + shippedOn + ", finalAmount=" + finalAmount + ", isAmountPaid=" + isAmountPaid + ", customerId=" + customerId + ", productsList=" + productsList + ", paymentOrderNumber=" + paymentOrderNumber + ", paymentIdNo=" + paymentIdNo + ", customer=" + customer + ", shippingCharge=" + shippingCharge + ", paymentStatus=" + paymentStatus + ", paymentErrorCode=" + paymentErrorCode + ", paymentErrorDescription=" + paymentErrorDescription + ", paymentErrorSource=" + paymentErrorSource + ", paymentErrorStep=" + paymentErrorStep + ", paymentErrorReason=" + paymentErrorReason + ", bankTransactionId=" + bankTransactionId + ", rrn=" + rrn + ", authCode=" + authCode + ", upiTransactionId=" + upiTransactionId + ", billingAddress=" + billingAddress + ", shippingAddress=" + shippingAddress + '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
