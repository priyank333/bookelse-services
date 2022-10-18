/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class ProductImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(updatable = false)
    private Long productImageId;
    private String imageURL;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String imageName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imgDirectory;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "productId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Product product;

    public ProductImage() {
    }

    public ProductImage(Long productImageId) {
        this.productImageId = productImageId;
    }

    public ProductImage(Long productImageId, String imageURL, String imageName) {
        this.productImageId = productImageId;
        this.imageURL = imageURL;
        this.imageName = imageName;
    }

    public ProductImage(String imageURL, String imageName, String imgDirectory, Product product) {
        this.imageURL = imageURL;
        this.imageName = imageName;
        this.imgDirectory = imgDirectory;
        this.product = product;
    }

    public ProductImage(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(Long productImageId) {
        this.productImageId = productImageId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImgDirectory() {
        return imgDirectory;
    }

    public void setImgDirectory(String imgDirectory) {
        this.imgDirectory = imgDirectory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductImage{" + "productImageId=" + productImageId + ", imageURL=" + imageURL + ", imageName="
                + imageName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.productImageId);
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
        final ProductImage other = (ProductImage) obj;
        if (!Objects.equals(this.productImageId, other.productImageId)) {
            return false;
        }
        return true;
    }

}
