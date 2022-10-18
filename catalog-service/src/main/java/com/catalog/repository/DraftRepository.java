/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.repository;

import com.catalog.config.sql.SqlQueries;
import com.catalog.mapper.DraftMapper;
import com.catalog.model.Draft;
import com.catalog.model.Product;
import com.catalog.model.ProductImage;
import com.catalog.model.ProductsInDraft;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Priyank Agrawal
 */
@Repository
public class DraftRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    @Autowired
    ProductImageRepository imageRepository;

    public Draft getDraftByCustomerId(String customerId) {
        String sqlQuery = sqlQueries.listDrafts;
        StringBuilder whereCondition = new StringBuilder();
        if (customerId != null) {
            whereCondition.append(" where ");
            addCustomerId(customerId, whereCondition);
            sqlQuery = sqlQuery.concat(whereCondition.toString());
        } else {
            return null;
        }
        Map<Long, Draft> draftMap = jdbcTemplate.query(sqlQuery, new DraftMapper());
        if (draftMap != null && !draftMap.values().isEmpty()) {
            List<Draft> draftList = draftMap.values().stream().collect(toCollection(ArrayList::new));
            Draft draft = draftList.get(0);
            Set<String> products = new HashSet<>();
            draft.getProductsInDraft().stream().map(product -> {
                product.getProduct().setProductImages(new ArrayList<>());
                return product;
            }).forEachOrdered(product -> {
                products.add(product.getProduct().getProductId());
            });
            List<ProductImage> images = imageRepository.listProductImagesByProducts(products);
            images.forEach(productImage -> {
                String pId = productImage.getProduct().getProductId();
                for (ProductsInDraft productInDraft : draft.getProductsInDraft()) {
                    Product product = productInDraft.getProduct();
                    if (product.getProductId().equals(pId)) {
                        product.getProductImages().add(productImage);
                    }
                }
            });
            return draft;
        } else {
            return null;
        }

    }

    public Draft getDraftByDraftId(Long draftId) {
        String sqlQuery = sqlQueries.listDrafts;
        StringBuilder whereCondition = new StringBuilder();
        if (draftId != null) {
            whereCondition.append(" where ");
            addDraftId(draftId, whereCondition);
            sqlQuery = sqlQuery.concat(whereCondition.toString());
        } else {
            return null;
        }
        Map<Long, Draft> draft = jdbcTemplate.query(sqlQuery, new DraftMapper());
        if (draft != null && !draft.values().isEmpty()) {
            List<Draft> draftList = draft.values().stream().collect(toCollection(ArrayList::new));
            return draftList.get(0);
        } else {
            return null;
        }
    }

    public void addCustomerId(String customerId, StringBuilder whereCondition) {
        if (customerId != null) {
            whereCondition.append(" d.customer_id = '").append(customerId).append("'");
        }
    }

    public void addDraftId(Long draftId, StringBuilder whereCondition) {
        if (draftId != null) {
            whereCondition.append(" d.draft_id = ").append(draftId);
        }
    }
}
