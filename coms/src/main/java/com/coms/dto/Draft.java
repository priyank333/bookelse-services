/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author z0043uwn
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Draft {

    private Long draftId;
    private String customerId;
    private List<ProductsInDraft> productsInDraft;
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
        return "Draft{" + "draftId=" + draftId + ", customerId=" + customerId + ", productsInDraft=" + productsInDraft + ", shippingCharge=" + shippingCharge + '}';
    }

}
