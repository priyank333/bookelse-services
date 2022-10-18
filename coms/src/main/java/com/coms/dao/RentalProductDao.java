/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dao;

import com.coms.model.RentalProduct;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author z0043uwn
 */
public interface RentalProductDao extends
        CrudRepository<RentalProduct, Long> {

    @Query("DELETE FROM RentalProduct p "
            + "WHERE p.customerOrder.orderNumber IN (:orders)")
    @Transactional
    @Modifying
    public Integer deleteByCustomerOrders(
            @Param("orders") List<String> orders);

    @Query("UPDATE RentalProduct rp SET rp.isPeriodExtended = :isPeriodExtended "
            + "where rp.rentalProductId = :rentalProductId")
    @Transactional
    @Modifying
    public Integer updateProductExtend(
            @Param("rentalProductId") String rentalProductId,
            @Param("isPeriodExtended") Boolean isExtended);

    @Query("UPDATE RentalProduct rp SET rp.extendedOn = :extendedOn "
            + "where rp.rentalProductId = :rentalProductId")
    @Transactional
    @Modifying
    public Integer updateProductExtendedOn(
            @Param("rentalProductId") String rentalProductId,
            @Param("extendedOn") LocalDate extendedOn);

    @Query("UPDATE RentalProduct rp "
            + "SET rp.isEligibleForReturn = :isEligibleForReturn "
            + "where rp.rentalProductId = :rentalProductId")
    @Transactional
    @Modifying
    public Integer updateEligibilityForReturnByRPID(
            @Param("rentalProductId") String rentalProductId,
            @Param("isEligibleForReturn") Boolean isEligibleForReturn);

    @Query("UPDATE RentalProduct rp "
            + "SET rp.isEligibleForReturn = :isEligibleForReturn "
            + "where rp.soldProduct.soldProductId = :soldProductId")
    @Transactional
    @Modifying
    public Integer updateEligibilityForReturnBySPID(
            @Param("soldProductId") Long soldProductId,
            @Param("isEligibleForReturn") Boolean isEligibleForReturn);

    @Query("UPDATE RentalProduct rp "
            + "SET rp.isLocked = :isLocked, rp.lockReason = :lockReason "
            + "where rp.soldProduct.soldProductId = :soldProductId")
    @Transactional
    @Modifying
    public Integer updateLockStatusBySPID(
            @Param("isLocked") Boolean isLocked,
            @Param("lockReason") String lockReason,
            @Param("soldProductId") Long soldProductId);

}
