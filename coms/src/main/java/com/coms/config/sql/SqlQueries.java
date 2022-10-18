/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.config.sql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author z0043uwn
 */
@Component
@PropertySource("classpath:config/sql/queries.properties")
public class SqlQueries {

    @Value("${listAllCustomerOrders}")
    public String listAllCustomerOrders;

    @Value("${listAllRentalProducts}")
    public String listAllRentalProducts;

    @Value("${getSoldProductForReturn}")
    public String getSoldProductForReturn;

    @Value("${getRentalProductDetails}")
    public String getRentalProductDetails;

    @Value("${listAllReturnRequests}")
    public String listAllReturnRequests;

    @Value("${countProductsByOrder}")
    public String countProductsByOrder;

    @Value("${deleteSoldProductByOrder}")
    public String deleteSoldProductByOrder;

    @Value("${listAllCustomerOrdersForAdmin}")
    public String listAllCustomerOrdersForAdmin;

    @Value("${getRentalProductById}")
    public String getRentalProductById;

    @Value("${makeEntryInRentalProduct}")
    public String makeEntryInRentalProduct;

    @Value("${listAllRentalProductsForAdmin}")
    public String listAllRentalProductsForAdmin;

    @Value("${getOrderInfoById}")
    public String getOrderInfoById;

    @Value("${getStockAndProductBySoldProduct}")
    public String getStockAndProductBySoldProduct;

    @Value("${getSoldProductByReturnRequest}")
    public String getSoldProductByReturnRequest;

    @Value("${listDelayedRentalItems}")
    public String listDelayedRentalItems;

    @Value("${updateRentalProductDelayedCharges}")
    public String updateRentalProductDelayedCharges;
}
