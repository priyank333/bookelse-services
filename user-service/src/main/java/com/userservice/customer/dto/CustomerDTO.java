/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.dto;

/**
 *
 * @author Priyank Agrawal
 */
public class CustomerDTO {

    private String customerId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String contact;
    private String addressLine1;
    private String addressLine2;
    private String pincode;
    private String area;
    private String city;
    private String state;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getIsEmailIdVerified() {
        return isEmailIdVerified;
    }

    public void setIsEmailIdVerified(Boolean isEmailIdVerified) {
        this.isEmailIdVerified = isEmailIdVerified;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" + "customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", emailId=" + emailId + ", contact=" + contact + ", addressLine1=" + addressLine1 + ", addressLine2="
                + addressLine2 + ", pincode=" + pincode + ", area=" + area + ", city=" + city + ", state=" + state
                + ", isEmailIdVerified=" + isEmailIdVerified + '}';
    }

}
