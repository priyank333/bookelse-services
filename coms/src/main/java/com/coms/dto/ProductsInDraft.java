/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

import com.coms.model.SoldProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author z0043uwn
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsInDraft {

    private Long productsInDraftId;
    @JsonProperty("product")
    private SoldProduct soldProduct;
    private Integer quantity;

    public Long getProductsInDraftId() {
        return productsInDraftId;
    }

    public void setProductsInDraftId(Long productsInDraftId) {
        this.productsInDraftId = productsInDraftId;
    }

    public SoldProduct getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(SoldProduct soldProduct) {
        this.soldProduct = soldProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.soldProduct.setQuantity(quantity);
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductsInDraft{" + "productsInDraftId=" + productsInDraftId + ", soldProduct=" + soldProduct + ", quantity=" + quantity + '}';
    }

}
