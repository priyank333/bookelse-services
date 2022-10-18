/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class Address implements Serializable {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "cityId", nullable = false)
    private City city;
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "stateId", nullable = false)
    private State state;
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "areaId", nullable = false)
    private Area area;
    @Column(nullable = false)
    @NotBlank
    private String addressLine1;
    @Column(nullable = false)
    @NotBlank
    private String addressLine2;
    @Column(nullable = false)
    @NotNull
    private AddressType addressType;
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "customerId", nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Customer customer;

    public Address() {
    }

    public Address(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Address{" + "addressId=" + addressId + ", city=" + city + ", state=" + state + ", area=" + area + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressType=" + addressType + ", customer=" + customer + '}';
    }

}
