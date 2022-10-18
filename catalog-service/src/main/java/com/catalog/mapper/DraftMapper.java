/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.mapper;

import com.catalog.model.Category;
import com.catalog.model.Draft;
import com.catalog.model.Product;
import com.catalog.model.ProductsInDraft;
import java.sql.ResultSet;
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
public class DraftMapper implements ResultSetExtractor<Map<Long, Draft>> {

    @Override
    public Map<Long, Draft> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Draft> draftMapper = new HashMap<>();
        while (rs.next()) {
            Long draftId = rs.getLong("draft_id");
            Draft draft = draftMapper.get(draftId);
            if (draft == null) {
                draft = new Draft();
                draftMapper.put(draftId, draft);
                draft.setDraftId(draftId);
                draft.setCustomerId(rs.getString("customer_id"));
                List<ProductsInDraft> productsInDraftList = new ArrayList<>();
                draft.setProductsInDraft(productsInDraftList);
                setProductDetailsInDraft(productsInDraftList, rs);
            } else {
                List<ProductsInDraft> productsInDraftList = draft.getProductsInDraft();
                if (productsInDraftList != null) {
                    setProductDetailsInDraft(productsInDraftList, rs);
                }
            }
        }
        return draftMapper;
    }

    private void setProductDetailsInDraft(List<ProductsInDraft> productsInDraftList, ResultSet rs) throws SQLException {
        ProductsInDraft productsInDraft = new ProductsInDraft();
        productsInDraftList.add(productsInDraft);
        productsInDraft.setProductsInDraftId(rs.getLong("products_in_draft_id"));
        productsInDraft.setQuantity(rs.getInt("quantity"));
        Product product = new Product(rs.getString("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductPrice(rs.getDouble("product_price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setDiscount(rs.getDouble("discount"));
        product.setRemarks(rs.getString("remarks"));
        product.setIsProductOnRent(rs.getBoolean("is_product_on_rent"));

        Long rentalBookId = rs.getLong("rental_book_id");
        Long rentalSetId = rs.getLong("rental_set_id");
        if (rentalBookId != 0l) {
            product.setDepreciation(rs.getDouble("rental_book_depreciation"));
        } else if (rentalSetId != 0l) {
            product.setDepreciation(rs.getDouble("rental_book_set_depreciation"));
        }

        product.setMaxDayForReturn(rs.getInt("max_day_for_return"));
        product.setCategory(new Category(rs.getString("category")));
        productsInDraft.setProduct(product);
    }

}
