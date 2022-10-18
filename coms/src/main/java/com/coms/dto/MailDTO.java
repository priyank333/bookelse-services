/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author z0043uwn
 */
public class MailDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate orderDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String orderNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductDTO> productList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double shippingCharge;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate shippedOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentMode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double payableAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String shippingAddress;
    private String emailAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String customerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate deliveredOn;

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

    public Double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(Double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public LocalDate getShippedOn() {
        return shippedOn;
    }

    public void setShippedOn(LocalDate shippedOn) {
        this.shippedOn = shippedOn;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(Double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(LocalDate deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    @Override
    public String toString() {
        return "MailDTO{" + "orderDate=" + orderDate + ", orderNumber=" + orderNumber + ", productList=" + productList + ", shippingCharge=" + shippingCharge + ", shippedOn=" + shippedOn + ", paymentMode=" + paymentMode + ", payableAmount=" + payableAmount + ", firstName=" + firstName + ", lastName=" + lastName + ", shippingAddress=" + shippingAddress + ", emailAddress=" + emailAddress + ", customerId=" + customerId + ", deliveredOn=" + deliveredOn + '}';
    }

}
