/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.repository;

import com.catalog.config.sql.SqlQueries;
import com.catalog.mapper.ProductImageMapper;
import com.catalog.model.ProductImage;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Priyank Agrawal
 */
@Repository
public class ProductImageRepository {

        @Autowired
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        @Autowired
        private SqlQueries sqlQueries;

        public List<ProductImage> listProductImagesByProduct(String productId) {
                SqlParameterSource parameterSource = new MapSqlParameterSource("productId", productId);

                List<ProductImage> productImages = namedParameterJdbcTemplate.query(
                                sqlQueries.listProductImagesByProduct, parameterSource, new ProductImageMapper());

                return productImages;
        }

        public List<ProductImage> listProductImagesByProducts(Set<String> productIds) {
                SqlParameterSource parameterSource = new MapSqlParameterSource("productIds", productIds);
                List<ProductImage> productImages = namedParameterJdbcTemplate.query(sqlQueries.listPImagesByPIds,
                                parameterSource, new ProductImageMapper());

                return productImages;
        }

}
