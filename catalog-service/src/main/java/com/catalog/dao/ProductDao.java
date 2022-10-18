/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.dao;

import com.catalog.model.Product;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Priyank Agrawal
 */
public interface ProductDao extends PagingAndSortingRepository<Product, String> {

        @Transactional
        @Modifying
        @Query("UPDATE Product p set p.quantity = p.quantity - :quantity " + "WHERE p.productId = :productId")
        public Integer deductProductQty(@Param("quantity") Integer quantity, @Param("productId") String productId);

        @Transactional
        @Modifying
        @Query("UPDATE Product p set p.quantity = :quantity WHERE p.productId = :productId")
        public Integer setProductQty(@Param("quantity") Integer quantity, @Param("productId") String productId);

        @Query(value = "select p.quantity - p.reserve_quantity from product p "
                        + "where p.product_id = :productId", nativeQuery = true)
        public Integer getQuantity(@Param("productId") String productId);

        @Query(value = "select p.quantity from product p " + "where p.product_id = :productId", nativeQuery = true)
        public Integer getActualQuantity(@Param("productId") String productId);

        @Transactional
        @Modifying
        @Query(value = "update product set reserve_quantity = reserve_quantity + :quantity where product_id = :productId", nativeQuery = true)
        public Integer reserveQuantity(@Param("productId") String productId, @Param("quantity") Integer quantity);

        @Transactional
        @Modifying
        @Query(value = "update product set reserve_quantity = reserve_quantity - :quantity where product_id = :productId and reserve_quantity > 0", nativeQuery = true)
        public Integer releaseQuantity(@Param("productId") String productId, @Param("quantity") Integer quantity);

}
