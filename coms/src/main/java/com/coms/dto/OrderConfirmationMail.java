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
public class OrderConfirmationMail {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate orderDate;
    private String orderNumber;
    private List<ProductDTO> productList;
    private Double shippingCharge;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate orderedOn;
    private String paymentMode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double payableAmount;
    private String firstName;
    private String lastName;

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

    public LocalDate getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(LocalDate orderedOn) {
        this.orderedOn = orderedOn;
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

    @Override
    public String toString() {
        return "OrderConfirmationMail{" + "orderDate=" + orderDate + ", orderNumber=" + orderNumber + ", productList=" + productList + ", shippingCharge=" + shippingCharge + ", orderedOn=" + orderedOn + ", paymentMode=" + paymentMode + ", payableAmount=" + payableAmount + ", firstName=" + firstName + ", lastName=" + lastName + '}';
    }

}
