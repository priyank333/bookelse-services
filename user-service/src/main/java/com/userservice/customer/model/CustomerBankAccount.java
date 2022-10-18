/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class CustomerBankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long customerBankAccountId;
    @NotBlank
    private String ifscCode;
    @NotBlank
    private String bankName;
    @NotBlank
    private String bankCode;
    @NotBlank
    private String accountNumber;

    public CustomerBankAccount() {
    }

    public CustomerBankAccount(Long customerBankAccountId) {
        this.customerBankAccountId = customerBankAccountId;
    }

    public Long getCustomerBankAccountId() {
        return customerBankAccountId;
    }

    public void setCustomerBankAccountId(Long customerBankAccountId) {
        this.customerBankAccountId = customerBankAccountId;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "CustomerBankAccount{" + "customerBankAccountId=" + customerBankAccountId + ", ifscCode=" + ifscCode
                + ", bankName=" + bankName + ", bankCode=" + bankCode + ", accountNumber=" + accountNumber + '}';
    }

}
