/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Vendor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long vendorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    @NotBlank
    private String vendorName;
    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String vendorContact;
    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String vendorAddress;

    public Vendor() {
    }

    public Vendor(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorContact() {
        return vendorContact;
    }

    public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    @Override
    public String toString() {
        return "Vendor{" + "vendorId=" + vendorId + ", vendorName=" + vendorName + ", vendorContact=" + vendorContact
                + ", vendorAddress=" + vendorAddress + '}';
    }

}
