/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.mapper;

import com.coms.model.SoldProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author z0043uwn
 */
public class SoldProductMapper implements ResultSetExtractor<Map<Long, SoldProduct>> {

    @Override
    public Map<Long, SoldProduct> extractData(final ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, SoldProduct> soldProductMap = new HashMap<>();

        while (rs.next()) {
            Long soldProductId = rs.getLong("sold_product_id");
            SoldProduct soldProduct = new SoldProduct(soldProductId);
            soldProduct.setDiscount(rs.getDouble("discount"));
            soldProduct.setIsProductOnRent(rs.getBoolean("is_product_on_rent"));
            soldProduct.setMaxDayForReturn(rs.getInt("max_day_for_return"));
            soldProduct.setProductId(rs.getString("product_id"));
            soldProduct.setSellPrice(rs.getDouble("sell_price"));
            soldProduct.setProductName(rs.getString("product_name"));
            soldProduct.setQuantity(rs.getInt("quantity"));
            soldProduct.setRemarks(rs.getString("remarks"));
            soldProductMap.put(soldProductId, soldProduct);
        }
        return soldProductMap;
    }

}
