/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.requestpayload;

import com.catalog.model.Vendor;
import java.time.LocalDate;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Priyank Agrawal
 */
@Validated
public class StockItem {

    public Vendor vendor;
    @Positive
    public Integer purchasedQuantity;
    @Positive
    public Double purchasedPrice;
    public LocalDate purchasedOn;

    @Override
    public String toString() {
        return "StockItem{" + "vendor=" + vendor + ", purchasedQuantity=" + purchasedQuantity + ", purchasedPrice="
                + purchasedPrice + ", purchasedOn=" + purchasedOn + '}';
    }

}
