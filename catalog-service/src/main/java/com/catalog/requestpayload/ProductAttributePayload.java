/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.requestpayload;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 * @author Priyank Agrawal
 */
public class ProductAttributePayload {

    public String productId;
    public String productName;
    public List<String> categories;
    @PositiveOrZero
    public Double lowestPrice;
    @PositiveOrZero
    public Double highestPrice;
    @PositiveOrZero
    public Double lowestDiscount;
    @PositiveOrZero
    @Max(100)
    public Double highestDiscount;
    public BookAttribute bookAttribute;
    public RentalBookAttribute rentalBookAttribute;
    public RentalBookSetAttribute rentalBookSetAttribute;
    public BookSetAttribute bookSetAttribute;
    public Boolean showBookSet;
    public Boolean showRentalBook;
    public Boolean showBook;
    public Boolean showRentalBookSet;

    @Override
    public String toString() {
        return "ProductAttributePayload{" + "productId=" + productId + ", productName=" + productName + ", categories="
                + categories + ", lowestPrice=" + lowestPrice + ", highestPrice=" + highestPrice + ", lowestDiscount="
                + lowestDiscount + ", highestDiscount=" + highestDiscount + ", bookAttribute=" + bookAttribute
                + ", rentalBookAttribute=" + rentalBookAttribute + ", rentalBookSetAttribute=" + rentalBookSetAttribute
                + ", bookSetAttribute=" + bookSetAttribute + ", showBookSet=" + showBookSet + ", showRentalBook="
                + showRentalBook + ", showBook=" + showBook + ", showRentalBookSet=" + showRentalBookSet + '}';
    }

}
