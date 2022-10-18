/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.config.sql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Priyank Agrawal
 */
@Component
@PropertySource("classpath:config/sql/queries.properties")
public class SqlQueries {

    @Value("${listAllRentalBookProducts}")
    public String listAllRentalBookProducts;

    @Value("${listAllRentalBookSetProducts}")
    public String listAllRentalBookSetProducts;

    @Value("${listAllBookSetProducts}")
    public String listAllBookSetProducts;

    @Value("${listAllBookProducts}")
    public String listAllBookProducts;

    @Value("${listDrafts}")
    public String listDrafts;

    @Value("${getPurchasedQuantityByProductId}")
    public String getPurchasedQuantityByProductId;

    @Value("${listProductImagesByProduct}")
    public String listProductImagesByProduct;

    @Value("${listIsProductSoldByProduct}")
    public String listIsProductSoldByProduct;

    @Value("${deleteItemStockByProduct}")
    public String deleteItemStockByProduct;

    @Value("${listUnsoldActiveStockItems}")
    public String listUnsoldActiveStockItems;

    @Value("${updateIsProductSoldInStock}")
    public String updateIsProductSoldInStock;

    @Value("${isStockItemListExist}")
    public String isStockItemListExist;

    @Value("${listProductIdByStockItem}")
    public String listProductIdByStockItem;

    @Value("${listIsProductSoldByStockItems}")
    public String listIsProductSoldByStockItems;

    @Value("${listStockItems}")
    public String listStockItems;

    @Value("${getProductIdByStock}")
    public String getProductIdByStock;

    @Value("${listPImagesByPIds}")
    public String listPImagesByPIds;
    

}
