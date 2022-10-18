/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.util;

import com.catalog.model.Product;
import com.catalog.model.ProductsInDraft;
import com.catalog.requestpayload.StockItem;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Priyank Agrawal
 */
public class ProductServiceUtil {

    private static final NumberFormat FORMATTER = new DecimalFormat("#0.0");

    public static Double countProductPrice(Product product) {
        Double finalPrice = product.getProductPrice() 
                * (1 - (product.getDiscount() / 100)) * product.getQuantity();
        return Double.parseDouble(FORMATTER.format(finalPrice));
    }

    public static double getTotalAmountOfDraft(List<ProductsInDraft> productsInDraftList) {
        double totalAmount = 0;
        for (ProductsInDraft productsInDraft : productsInDraftList) {
            totalAmount += countProductPrice(productsInDraft.getProduct());
        }
        return totalAmount;
    }

    public static int getTotalCountFromStock(List<StockItem> stockItems) {
        int productCount = 0;
        for (StockItem stockItem : stockItems) {
            productCount += stockItem.purchasedQuantity;
        }
        return productCount;
    }

    public static int sumOfArray(int input[]) {
        int sum = 0;
        for (int eachInput : input) {
            sum += eachInput;
        }
        return sum;
    }

    public static String generateProductId() {
        RandomString productId = new RandomString(8, ThreadLocalRandom.current());
        return productId.nextString().toUpperCase();
    }
    
}
