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
public class AcquirerData {

    @JsonProperty("bank_transaction_id")
    private String bankTransactionId;

    @JsonProperty("rrn")
    private String rrn;

    @JsonProperty("auth_code")
    private String authCode;

    @JsonProperty("upi_transaction_id")
    private String upiTransactionId;

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

    @Override
    public String toString() {
        return "AcquirerData{" + "bankTransactionId=" + bankTransactionId + ", rrn=" + rrn + ", authCode=" + authCode + ", upiTransactionId=" + upiTransactionId + '}';
    }

}
