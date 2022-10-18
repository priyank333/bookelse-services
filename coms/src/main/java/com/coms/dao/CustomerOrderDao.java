/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dao;

import com.coms.model.CustomerOrder;
import com.coms.model.OrderStatus;
import com.coms.model.PaymentStatus;
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
public interface CustomerOrderDao
        extends CrudRepository<CustomerOrder, String> {

    @Query(value = "select order_number from customer_order "
            + "where DATE_PART('minute', now()- ordered_date) > :minutes AND "
            + "order_status = 0",
            nativeQuery = true)
    public List<String> getPendingOrdersForLastMinutes(
            @Param("minutes") Integer minutesTime);

    @Query("DELETE FROM CustomerOrder co WHERE co.orderNumber IN (:orders) ")
    @Transactional
    @Modifying
    public Integer deleteByCustomerOrders(
            @Param("orders") List<String> orders);

    @Query("UPDATE CustomerOrder co SET co.orderStatus = :deliveryStatus,"
            + " co.deliveredOn = :deliveredOn "
            + "WHERE co.orderNumber = :orderNumber")
    @Transactional
    @Modifying
    public Integer updateDeliveryStatus(
            @Param("orderNumber") String orderNumber,
            @Param("deliveryStatus") OrderStatus deliveryStatus,
            @Param("deliveredOn") LocalDate deliveredOn);

    @Query("UPDATE CustomerOrder co SET co.orderStatus = :receiveStatus,"
            + " co.receivedOn = :receivedOn "
            + "WHERE co.orderNumber = :orderNumber")
    @Transactional
    @Modifying
    public Integer updateReceiveStatus(
            @Param("orderNumber") String orderNumber,
            @Param("receiveStatus") OrderStatus receiveStatus,
            @Param("receivedOn") LocalDate receivedOn);

    @Query("UPDATE CustomerOrder co SET co.orderStatus = :shippingStatus,"
            + " co.shippedOn = :shippedOn "
            + "WHERE co.orderNumber = :orderNumber")
    @Transactional
    @Modifying
    public Integer updateShippingStatus(
            @Param("orderNumber") String orderNumber,
            @Param("shippingStatus") OrderStatus shippingStatus,
            @Param("shippedOn") LocalDate deliveredOn);

    @Query("UPDATE CustomerOrder co SET co.isAmountPaid = :paymentStatus "
            + "WHERE co.orderNumber = :orderNumber")
    @Transactional
    @Modifying
    public Integer updatePaymentStatus(
            @Param("orderNumber") String orderNumber,
            @Param("paymentStatus") Boolean paymentStatus);

    @Query("UPDATE CustomerOrder co SET co.paymentMode = :paymentMode "
            + "WHERE co.orderNumber = :orderNumber")
    @Transactional
    @Modifying
    public Integer updatePaymentMode(
            @Param("orderNumber") String orderNumber,
            @Param("paymentMode") String paymentMode);

    @Query(value = "Select co.delivered_on from customer_order co where co.order_number = :orderNumber",
            nativeQuery = true)
    public LocalDate getDeliveredDateByOrderId(@Param("orderNumber") String orderNumber);

    @Query(value = "select co.order_status from customer_order co "
            + "where co.order_number = :orderNumber", nativeQuery = true)
    public Integer getOrderStatusByOrderId(@Param("orderNumber") String orderNumber);

    @Query(value = "select co.customer_id from customer_order co "
            + "where co.order_number = :orderNumber", nativeQuery = true)
    public String getCustomerIdByOrder(@Param("orderNumber") String orderNumber);

    @Query("UPDATE CustomerOrder co SET "
            + "co.paymentStatus = :paymentStatus, "
            + "co.bankTransactionId = :bankTransactionId, "
            + "co.authCode = :authCode, "
            + "co.rrn = :rrn, "
            + "co.paymentIdNo = :paymentIdNo, "
            + "co.isAmountPaid = :isAmountPaid, "
            + "co.paymentErrorCode = :paymentErrorCode, "
            + "co.paymentErrorDescription = :paymentErrorDescription, "
            + "co.paymentErrorSource = :paymentErrorSource, "
            + "co.paymentErrorStep = :paymentErrorStep, "
            + "co.paymentErrorReason = :paymentErrorReason, "
            + "co.paymentMode = :paymentMode,"
            + "co.upiTransactionId = :upiTransactionId "
            + "WHERE co.paymentOrderNumber = :paymentOrderNumber")
    @Transactional
    @Modifying
    public Integer updatePaymentStatus(
            @Param("paymentOrderNumber") String paymentOrderNumber,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("bankTransactionId") String bankTransactionId,
            @Param("rrn") String rrn,
            @Param("authCode") String authCode,
            @Param("upiTransactionId") String upiTransactionId,
            @Param("paymentIdNo") String paymentIdNo,
            @Param("isAmountPaid") Boolean isAmountPaid,
            @Param("paymentErrorCode") String paymentErrorCode,
            @Param("paymentErrorDescription") String paymentErrorDescription,
            @Param("paymentErrorSource") String paymentErrorSource,
            @Param("paymentErrorStep") String paymentErrorStep,
            @Param("paymentErrorReason") String paymentErrorReason,
            @Param("paymentMode") String paymentMode);

    public CustomerOrder findByPaymentOrderNumber(String paymentOrderNumber);
    
    @Query("SELECT co.shippingAddress FROM CustomerOrder co where co.orderNumber = :orderNumber") 
    public String findShippingAddressByOrderNumber(@Param("orderNumber") String orderNumber);
}
