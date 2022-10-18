/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dao;

import com.coms.model.ReturnRequest;
import com.coms.model.ReturnStatus;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author z0043uwn
 */
public interface ReturnRequestDao extends
        CrudRepository<ReturnRequest, Long> {

    @Query("UPDATE ReturnRequest rr SET rr.returnStatus = :returnStatus "
            + "WHERE rr.returnRequestId = :returnRequestId")
    @Transactional
    @Modifying
    public Integer updateReturnRequest(
            @Param("returnRequestId") UUID returnRequestId,
            @Param("returnStatus") ReturnStatus returnStatus);

    @Query("UPDATE ReturnRequest rr "
            + "SET rr.isAmountPaidToCustomer = :isAmountPaidToCustomer "
            + "WHERE rr.returnRequestId = :returnRequestId")
    @Transactional
    @Modifying
    public Integer updatePaymentStatus(
            @Param("returnRequestId") UUID returnRequestId,
            @Param("isAmountPaidToCustomer") Boolean isAmountPaidToCustomer);
}
