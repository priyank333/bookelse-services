/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.requestpayload;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Priyank Agrawal
 */
public class UpdateProductSoldPayload {

    @NotEmpty
    public List<Long> stockItemList;
    @NotNull
    public Boolean isProductSold;
    @NotNull
    public Boolean updateQuantity;
    public String productId;
}
