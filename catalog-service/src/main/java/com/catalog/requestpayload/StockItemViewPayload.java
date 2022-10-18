/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.requestpayload;

import java.time.LocalDate;
import javax.validation.constraints.Positive;

/**
 *
 * @author Priyank Agrawal
 */
public class StockItemViewPayload {
    @Positive
    public Long stockItemId;
    public Boolean isProductActive;
    public Boolean isProductReturnedToVendor;
    public Boolean isProductSold;
    public LocalDate fromPurchasedOn;
    public LocalDate toPurchasedOn;
    public String productId;
    @Positive
    public Long vendorId;
    public String productName;
}
