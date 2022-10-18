/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
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
public class Draft implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long draftId;
    @Column(nullable = false)
    @NotNull
    private String customerId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "draftId")
    @NotEmpty
    private List<ProductsInDraft> productsInDraft;
    @Column(nullable = true)
    @Transient
    private Double shippingCharge;

    public Draft() {
    }

    public Draft(Long draftId) {
        this.draftId = draftId;
    }

    public Long getDraftId() {
        return draftId;
    }

    public void setDraftId(Long draftId) {
        this.draftId = draftId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<ProductsInDraft> getProductsInDraft() {
        return productsInDraft;
    }

    public void setProductsInDraft(List<ProductsInDraft> productsInDraft) {
        this.productsInDraft = productsInDraft;
    }

    public Double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(Double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    @Override
    public String toString() {
        return "Draft{" + "draftId=" + draftId + ", customerId=" + customerId + ", productsInDraft=" + productsInDraft
                + ", shippingCharge=" + shippingCharge + '}';
    }

}
