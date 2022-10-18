/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.repository;

import com.coms.config.sql.SqlQueries;
import com.coms.model.SoldProduct;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author z0043uwn
 */
@Repository
public class SoldProductRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public SoldProduct getSoldProductForReturn(
            Long soldProductId, String orderNumber) {
        SqlParameterSource parameters
                = new MapSqlParameterSource("soldProductId", soldProductId);
        List<SoldProduct> soldProducts = namedParameterJdbcTemplate.query(
                sqlQueries.getSoldProductForReturn, parameters,
                (ResultSet rs, int i) -> {
                    SoldProduct soldProduct = new SoldProduct();
                    soldProduct.setSoldProductId(rs.getLong("sold_product_id"));
                    soldProduct.setSellPrice(rs.getDouble("sell_price"));
                    soldProduct.setDiscount(rs.getDouble("discount"));
                    soldProduct.setIsProductOnRent(
                            rs.getBoolean("is_product_on_rent"));
                    soldProduct.setMaxDayForReturn(
                            rs.getInt("max_day_for_return"));
                    soldProduct.setOrderNumber(rs.getString("order_number"));
                    soldProduct.setIsProductReturned(
                            rs.getBoolean("is_product_returned"));
                    soldProduct.setIsReturnRequestOpen(
                            rs.getBoolean("is_return_request_open"));
                    return soldProduct;
                });
        if (soldProducts.isEmpty()) {
            return null;
        } else {
            return soldProducts.get(0);
        }
    }

    public Map<String, Integer> countProductsByOrder(List<String> orderList) {
        SqlParameterSource parameters
                = new MapSqlParameterSource("orderNumberList", orderList);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(
                sqlQueries.countProductsByOrder, parameters);
        Map<String, Integer> productList = new HashMap<>();
        while (rowSet.next()) {
            productList.put(rowSet.getString("product_id"), rowSet.getInt("buy_quantity"));
        }
        return productList;
    }

    public Boolean deleteSoldProductByOrder(List<String> orderList) {
        SqlParameterSource parameters
                = new MapSqlParameterSource("orderNumberList", orderList);
        boolean isDeleted = namedParameterJdbcTemplate.update(sqlQueries.deleteSoldProductByOrder, parameters) > 0;
        return isDeleted;
    }

    public Map<String, Object> getProductAndStockIdBySoldProduct(Long soldProductId) {
        SqlParameterSource parameters
                = new MapSqlParameterSource("soldProductId", soldProductId);

        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(
                sqlQueries.getStockAndProductBySoldProduct, parameters);
        rowSet.next();
        Map<String, Object> response = new HashMap<>();
        response.put("productId", rowSet.getString("product_id"));
        response.put("stockItemId", rowSet.getLong("stock_item_id"));
        return response;
    }
}
