/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.repository;

import com.catalog.config.sql.SqlQueries;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Priyank Agrawal
 */
@Repository
public class ProductVendorRepository {

    @Autowired
    private SqlQueries sqlQueries;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<Long, Integer> getPurchasedQuantityByProductId(String productId) {
        SqlParameterSource parameters = new MapSqlParameterSource("productId", productId);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sqlQueries.getPurchasedQuantityByProductId,
                parameters);
        Map<Long, Integer> result = new HashMap<>();
        while (rowSet.next()) {
            result.put(rowSet.getLong("product_vendor_id"), rowSet.getInt("purchased_quantity"));
        }
        return result;
    }
}
