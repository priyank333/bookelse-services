/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.mapper;

import com.catalog.model.Product;
import com.catalog.model.StockItem;
import com.catalog.model.Vendor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Priyank Agrawal
 */
public class StockItemMapper implements ResultSetExtractor<List<StockItem>> {

    @Override
    public List<StockItem> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<StockItem> stockItems = new ArrayList<>();
        while (rs.next()) {
            StockItem stockItem = new StockItem(rs.getLong("stock_item_id"));
            stockItem.setIsProductActive(rs.getBoolean("is_product_active"));
            stockItem.setIsProductReturnedToVendor(rs.getBoolean("is_product_returned_to_vendor"));
            stockItem.setIsProductSold(rs.getBoolean("is_product_sold"));
            stockItem.setPurchasedOn(rs.getDate("purchased_on").toLocalDate());
            stockItem.setPurchasedPrice(rs.getDouble("purchased_price"));
            Product product = new Product();
            stockItem.setProduct(product);
            product.setProductId(rs.getString("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setProductPrice(rs.getDouble("product_price"));
            product.setDiscount(rs.getDouble("discount"));
            product.setIsProductOnRent(rs.getBoolean("is_product_on_rent"));
            Vendor vendor = new Vendor(rs.getLong("vendor_id"));
            stockItem.setVendor(vendor);
            vendor.setVendorName(rs.getString("vendor_name"));
            stockItems.add(stockItem);
        }
        return stockItems;
    }

}
