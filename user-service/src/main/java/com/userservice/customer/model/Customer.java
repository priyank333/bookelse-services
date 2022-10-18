/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.userservice.util.Hash;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
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
public class Customer implements Serializable {

    @Id
    @Column(updatable = false)
    private String customerId;
    @Column(nullable = false)
    @NotBlank
    private String firstName;
    @NotBlank
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String emailId;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String contact;
    @Column(nullable = false)
    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private Boolean isEmailVerified;
//    @Embedded
//    private Address address;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customerBankAccountId", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CustomerBankAccount customerBankAccount;
    private LocalDateTime registeredOn;
    private Boolean isActive;

    public Customer() {
    }

    public Customer(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new Hash().getHashValue(password);
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }

    public CustomerBankAccount getCustomerBankAccount() {
        return customerBankAccount;
    }

    public void setCustomerBankAccount(CustomerBankAccount customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

//    @Override
//    public String toString() {
//        return "Customer{" + "customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
//                + ", emailId=" + emailId + ", contact=" + contact + ", password=" + password + ", isEmailVerified="
//                + isEmailVerified + ", address=" + address + ", customerBankAccount=" + customerBankAccount
//                + ", registeredOn=" + registeredOn + ", isActive=" + isActive + '}';
//    }

}
