/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dao;

import com.coms.model.SoldProduct;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author z0043uwn
 */
public interface SoldProductDao extends
        CrudRepository<SoldProduct, Long> {

    @Query("UPDATE SoldProduct sp SET sp.isReturnRequestOpen = :status "
            + "where sp.soldProductId = :soldProductId")
    @Transactional
    @Modifying
    public Integer updateReturnRequestOpenStatus(
            @Param("soldProductId") Long soldProductId,
            @Param("status") Boolean status);

    @Query("UPDATE SoldProduct sp SET sp.isProductReturned = :status "
            + "where sp.soldProductId = :soldProductId")
    @Transactional
    @Modifying
    public Integer updateProductReturnStatus(
            @Param("soldProductId") Long soldProductId,
            @Param("status") Boolean status);

}
