/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.mapper;

import com.catalog.model.Product;
import com.catalog.model.ProductImage;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Priyank Agrawal
 */
public class ProductImageMapper implements ResultSetExtractor<List<ProductImage>> {

    private Map<String, Boolean> columnsInResultSet;

    @Override
    public List<ProductImage> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ProductImage> productImages = new ArrayList<>();
        setColumns(rs);
        while (rs.next()) {
            ProductImage productImage = new ProductImage();
            productImage.setImageName(rs.getString("image_name"));
            productImage.setProductImageId(rs.getLong("product_image_id"));
            productImage.setImageURL(rs.getString("imageurl"));
            if (columnsInResultSet.get("product_id") != null) {
                productImage.setProduct(new Product(rs.getString("product_id")));
            }
            productImages.add(productImage);
        }

        return productImages;

    }

    private void setColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        columnsInResultSet = new HashMap<>();
        int columns = resultSetMetaData.getColumnCount();
        for (int col = 1; col <= columns; col++) {
            columnsInResultSet.put(resultSetMetaData.getColumnName(col), true);
        }
    }
}
