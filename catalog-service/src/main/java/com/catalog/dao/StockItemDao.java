/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.dao;

import com.catalog.model.StockItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Priyank Agrawal
 */
public interface StockItemDao extends CrudRepository<StockItem, Long> {

        @Query("SELECT si.isProductSold from StockItem si " + "where si.stockItemId = :stockItemId")
        public Boolean getIsProductSoldByStockItem(@Param("stockItemId") Long stockItemId);

        @Query("SELECT si.isProductActive from StockItem si " + "where si.stockItemId = :stockItemId")
        public Boolean getIsProductActive(@Param("stockItemId") Long stockItemId);

        @Query("SELECT si.isProductReturnedToVendor from StockItem si " + "where si.stockItemId = :stockItemId")
        public Boolean getIsProductReturnedToVendor(@Param("stockItemId") Long stockItemId);

        @Transactional
        @Modifying
        @Query("UPDATE StockItem si set si.isProductActive = :isProductActive " + "WHERE si.stockItemId = :stockItemId")
        public Integer updateIsProductActive(@Param("stockItemId") Long stockItemId,
                        @Param("isProductActive") Boolean isProductActive);

        @Transactional
        @Modifying
        @Query("UPDATE StockItem si set " + "si.isProductReturnedToVendor = :isProductReturnedToVendor "
                        + "WHERE si.stockItemId = :stockItemId")
        public Integer updateIsProductReturnToVendor(@Param("stockItemId") Long stockItemId,
                        @Param("isProductReturnedToVendor") Boolean isProductReturnedToVendor);

}
