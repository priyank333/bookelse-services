/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
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
public class Product implements Serializable {

    @Id
    @Column(updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productId;

    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rentalBookId", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RentalBook rentalBook;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "setId", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BookSet bookSet;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rentalSetId", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RentalBookSet rentalBookSet;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bookId", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Book book;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false, precision = 2)
    private Double productPrice;

    @PositiveOrZero
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = true)
    private Double discount;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remarks;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = true)
    private Integer reserveQuantity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Category category;

    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isProductOnRent;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductImage> productImages;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double depreciation;

    @PositiveOrZero
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private Integer maxDayForReturn;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp maxReturnDate;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isAvailable;

    public Product() {
    }

    public Product(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public RentalBook getRentalBook() {
        return rentalBook;
    }

    public void setRentalBook(RentalBook rentalBook) {
        this.rentalBook = rentalBook;
    }

    public BookSet getBookSet() {
        return bookSet;
    }

    public void setBookSet(BookSet bookSet) {
        this.bookSet = bookSet;
    }

    public RentalBookSet getRentalBookSet() {
        return rentalBookSet;
    }

    public void setRentalBookSet(RentalBookSet rentalBookSet) {
        this.rentalBookSet = rentalBookSet;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReserveQuantity() {
        return reserveQuantity;
    }

    public void setReserveQuantity(Integer reserveQuantity) {
        this.reserveQuantity = reserveQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getIsProductOnRent() {
        return isProductOnRent;
    }

    public void setIsProductOnRent(Boolean isProductOnRent) {
        this.isProductOnRent = isProductOnRent;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Double depreciation) {
        this.depreciation = depreciation;
    }

    public Integer getMaxDayForReturn() {
        return maxDayForReturn;
    }

    public void setMaxDayForReturn(Integer maxDayForReturn) {
        this.maxDayForReturn = maxDayForReturn;
    }

    public Timestamp getMaxReturnDate() {
        return maxReturnDate;
    }

    public void setMaxReturnDate(Timestamp maxReturnDate) {
        this.maxReturnDate = maxReturnDate;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", rentalBook=" + rentalBook + ", bookSet=" + bookSet + ", rentalBookSet=" + rentalBookSet + ", book=" + book + ", productPrice=" + productPrice + ", discount=" + discount + ", remarks=" + remarks + ", quantity=" + quantity + ", reserveQuantity=" + reserveQuantity + ", category=" + category + ", isProductOnRent=" + isProductOnRent + ", productImages=" + productImages + ", depreciation=" + depreciation + ", maxDayForReturn=" + maxDayForReturn + ", maxReturnDate=" + maxReturnDate + ", isAvailable=" + isAvailable + '}';
    }

}
