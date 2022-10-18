/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.repository;

import com.catalog.config.sql.SqlQueries;
import com.catalog.mapper.StockItemMapper;
import com.catalog.model.StockItem;
import com.catalog.model.Vendor;
import static com.catalog.repository.util.RepositoryUtil.setDateRange;
import static com.catalog.repository.util.RepositoryUtil.setLike;
import static com.catalog.repository.util.RepositoryUtil.setValue;
import com.catalog.requestpayload.StockItemViewPayload;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author z0043uwn
 */
@Repository
public class StockItemRepository {

    @Autowired
    private SqlQueries sqlQueries;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<Long, Boolean> listIsProductSoldByProduct(String productId) {
        SqlParameterSource parameters = new MapSqlParameterSource("productId", productId);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sqlQueries.listIsProductSoldByProduct,
                parameters);
        Map<Long, Boolean> result = new HashMap<>();
        while (rowSet.next()) {
            result.put(rowSet.getLong("stock_item_id"), rowSet.getBoolean("is_product_sold"));
        }
        return result;
    }

    public Map<Long, Boolean> listIsProductSoldByStockItems(List<Long> stockItems) {
        SqlParameterSource parameters = new MapSqlParameterSource("stockItems", stockItems);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sqlQueries.listIsProductSoldByStockItems,
                parameters);
        Map<Long, Boolean> result = new HashMap<>();
        while (rowSet.next()) {
            result.put(rowSet.getLong("stock_item_id"), rowSet.getBoolean("is_product_sold"));
        }
        return result;
    }

    public Boolean deleteItemStockByProduct(String productId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("productId", productId);
        int rowAffected = namedParameterJdbcTemplate.update(sqlQueries.deleteItemStockByProduct, parameters);
        return rowAffected != 0;
    }

    public List<StockItem> listUnsoldActiveStockItemsByProductId(String productId, Integer quantity) {
        Map<String, Object> inputParam = new HashMap<>();
        inputParam.put("quantity", quantity);
        inputParam.put("productId", productId);
        List<StockItem> stockItems = namedParameterJdbcTemplate.query(sqlQueries.listUnsoldActiveStockItems,
                inputParam, (ResultSet rs, int i) -> {
                    StockItem stockItem = new StockItem(rs.getLong("stock_item_id"));
                    stockItem.setPurchasedOn(rs.getDate("purchased_on").toLocalDate());
                    stockItem.setPurchasedPrice(rs.getDouble("purchased_price"));
                    stockItem.setVendor(new Vendor(rs.getLong("vendor_id")));
                    return stockItem;
                });

        return stockItems;
    }

    public int[] batchUpdateStockItemsIsSold(List<Long> stockItemList, Boolean flag) {
        SqlParameterSourceUtils.createBatch();
        return jdbcTemplate.batchUpdate(sqlQueries.updateIsProductSoldInStock,
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                ps.setBoolean(1, flag);
                ps.setLong(2, stockItemList.get(index));
            }

            @Override
            public int getBatchSize() {
                return stockItemList.size();
            }
        });
    }

    public Integer countItemsByList(List<Long> stockItems) {
        Map<String, List<Long>> params = new HashMap<>();
        params.put("stockItems", stockItems);
        return namedParameterJdbcTemplate.queryForObject(sqlQueries.isStockItemListExist, params,
                Integer.class);
    }

    public List<String> listProductIdByStockItems(List<Long> stockItems) {
        Map<String, List<Long>> params = new HashMap<>();
        params.put("stockItems", stockItems);
        return namedParameterJdbcTemplate.query(sqlQueries.listProductIdByStockItem, params,
                new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getString("product_id");
            }
        });
    }

    public List<StockItem> listStockItems(StockItemViewPayload itemViewPayload) {
        String sqlQuery = sqlQueries.listStockItems + generateWhereCondition(itemViewPayload);
        List<StockItem> stockItems = jdbcTemplate.query(sqlQuery, new StockItemMapper());
        return stockItems;
    }

    public String getProductIdByStock(Long stockItemId) {
        Map<String, Long> params = new HashMap<>();
        params.put("stockItemId", stockItemId);
        return namedParameterJdbcTemplate.query(sqlQueries.getProductIdByStock, params, (ResultSet rs) -> {
            if (rs.next()) {
                return rs.getString("product_id");
            } else {
                return null;
            }
        });
    }

    private String generateWhereCondition(StockItemViewPayload itemViewPayload) {
        StringBuilder whereCondition = new StringBuilder();
        Boolean isProductActive = itemViewPayload.isProductActive;
        Boolean isProductReturnedToVendor = itemViewPayload.isProductReturnedToVendor;
        Boolean isProductSold = itemViewPayload.isProductSold;
        String productId = itemViewPayload.productId;
        Long stockItemId = itemViewPayload.stockItemId;
        Long vendorId = itemViewPayload.vendorId;
        LocalDate fromPurchasedOn = itemViewPayload.fromPurchasedOn;
        LocalDate toPurchasedOn = itemViewPayload.toPurchasedOn;
        String productName = itemViewPayload.productName;
        if (isProductActive != null) {
            setValue(isProductActive, "si", "is_product_active", whereCondition);
        }
        if (isProductReturnedToVendor != null) {
            setValue(isProductReturnedToVendor, "si", "is_product_returned_to_vendor",
                    whereCondition);
        }
        if (isProductSold != null) {
            setValue(isProductSold, "si", "is_product_sold", whereCondition);
        }
        if (productId != null) {
            String trimmedValue = productId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "si", "product_id", whereCondition);
            }
        }
        if (stockItemId != null) {
            setValue(stockItemId, "si", "stock_item_id", whereCondition);
        }
        if (vendorId != null) {
            setValue(vendorId, "si", "vendor_id", whereCondition);
        }
        if (fromPurchasedOn != null && toPurchasedOn != null) {
            setDateRange(fromPurchasedOn, toPurchasedOn, "si", "purchased_on",
                    whereCondition);
        }
        if (productName != null) {
            String trimmedValue = productName.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "p", "product_name", whereCondition);
            }
        }
        return whereCondition.toString();
    }
}
