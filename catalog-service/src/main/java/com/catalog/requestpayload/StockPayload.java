/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.requestpayload;

import com.catalog.model.Product;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Priyank Agrawal
 */
public class StockPayload {

    @NotNull
    public Product product;
    @NotEmpty
    public List<StockItem> stockItems;

    @Override
    public String toString() {
        return "StockPayload{" + "product=" + product + ", stockItems=" + stockItems + '}';
    }

}
