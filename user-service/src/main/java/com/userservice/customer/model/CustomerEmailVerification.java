/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class CustomerEmailVerification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", nullable = false)
    private Long customerEmailVerificationId;
    private String verificationCode;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private OTPFor oTPFor;
    private LocalDateTime generatedOn;

    public CustomerEmailVerification() {

    }

    public CustomerEmailVerification(String verificationCode, Customer customer, OTPFor oTPFor) {
        generatedOn = LocalDateTime.now();
        this.verificationCode = verificationCode;
        this.customer = customer;
        this.oTPFor = oTPFor;
    }

    public Long getCustomerEmailVerificationId() {
        return customerEmailVerificationId;
    }

    public void setCustomerEmailVerificationId(Long customerEmailVerificationId) {
        this.customerEmailVerificationId = customerEmailVerificationId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OTPFor getoTPFor() {
        return oTPFor;
    }

    public void setoTPFor(OTPFor oTPFor) {
        this.oTPFor = oTPFor;
    }

    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Override
    public String toString() {
        return "CustomerEmailVerification{" + "customerEmailVerificationId=" + customerEmailVerificationId
                + ", verificationCode=" + verificationCode + ", customer=" + customer + ", oTPFor=" + oTPFor
                + ", generatedOn=" + generatedOn + '}';
    }

}
