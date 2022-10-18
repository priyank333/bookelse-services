/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

import java.io.Serializable;

/**
 *
 * @author Priyank Agrawal
 */
public class Customer implements Serializable {

    private String customerId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String contact;
    private Boolean isEmailIdVerified;

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

    public Boolean getIsEmailIdVerified() {
        return isEmailIdVerified;
    }

    public void setIsEmailIdVerified(Boolean isEmailIdVerified) {
        this.isEmailIdVerified = isEmailIdVerified;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId + ", contact=" + contact + ", isEmailIdVerified=" + isEmailIdVerified + '}';
    }

}
