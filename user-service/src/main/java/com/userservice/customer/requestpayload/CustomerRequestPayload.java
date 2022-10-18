/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.requestpayload;

/**
 *
 * @author Priyank Agrawal
 */
public class CustomerRequestPayload {

    public String customerId;
    public String contact;
    public String emailId;
    public String firstName;
    public String lastName;
    public String area;
    public Boolean isEmailVerified;
    public String pincode;
    public String city;
    public String state;
    public String bankCode;
    public String bankName;
    public String ifscCode;

    public CustomerRequestPayload() {
    }

    public CustomerRequestPayload(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerRequestPayload{" + "customerId=" + customerId + ", contact=" + contact + ", emailId=" + emailId
                + ", firstName=" + firstName + ", lastName=" + lastName + ", area=" + area + ", isEmailVerified="
                + isEmailVerified + ", pincode=" + pincode + ", city=" + city + ", state=" + state + ", bankCode="
                + bankCode + ", bankName=" + bankName + ", ifscCode=" + ifscCode + '}';
    }

}
