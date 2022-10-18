/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

/**
 *
 * @author z0043uwn
 */
public class OnlinePaymentOrderDetails {

    private String key;
    private Integer amount;
    private String currency;
    private String orderNumberForPayment;
    private String customerName;
    private String customerEmailId;
    private String orderNumber;
    private String customerContact;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderNumberForPayment() {
        return orderNumberForPayment;
    }

    public void setOrderNumberForPayment(String orderNumberForPayment) {
        this.orderNumberForPayment = orderNumberForPayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    @Override
    public String toString() {
        return "OnlinePaymentOrderDetails{" + "key=" + key + ", amount=" + amount + ", currency=" + currency + ", orderNumberForPayment=" + orderNumberForPayment + ", customerName=" + customerName + ", customerEmailId=" + customerEmailId + ", orderNumber=" + orderNumber + ", customerContact=" + customerContact + '}';
    }

}
