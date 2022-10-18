/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.requestbody.paymentauth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author z0043uwn
 */
public class Payload {

    @JsonProperty("payment")
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Payload{" + "payment=" + payment + '}';
    }

}
