/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.dao;

import com.catalog.model.Product;
import com.catalog.model.ProductImage;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Priyank Agrawal
 */
public interface ProductImageDao extends CrudRepository<ProductImage, Long> {

    @Modifying
    @Transactional
    public Integer deleteByImageURL(String imageURL);

    @Modifying
    @Transactional
    public Integer deleteByProduct(Product product);
}
