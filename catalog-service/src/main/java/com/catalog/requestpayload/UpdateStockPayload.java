package com.catalog.requestpayload;

import com.catalog.model.StockItem;
import javax.validation.constraints.NotNull;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Priyank Agrawal
 */
public class UpdateStockPayload {
    public StockItem stockItem;
    @NotNull
    public Boolean updateQuantity;
}
