/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class ProductsInDraft implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long productsInDraftId;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "productId")
    private Product product;
    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;

    public ProductsInDraft() {
    }

    public ProductsInDraft(Long productsInDraftId) {
        this.productsInDraftId = productsInDraftId;
    }

    public Long getProductsInDraftId() {
        return productsInDraftId;
    }

    public void setProductsInDraftId(Long productsInDraftId) {
        this.productsInDraftId = productsInDraftId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.product.getProductId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductsInDraft other = (ProductsInDraft) obj;
        if (!Objects.equals(this.product.getProductId(), other.product.getProductId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductsInDraft{" + "productsInDraftId=" + productsInDraftId + ", product=" + product + ", quantity="
                + quantity + '}';
    }

}
