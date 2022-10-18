/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.payload;

import com.coms.model.OrderStatus;
import java.time.LocalDate;

/**
 *
 * @author z0043uwn
 */
public class CustomerOrderPayload {

    public String orderNumber;
    public String customerId;
    public Boolean isAmountPaid;
    public OrderStatus orderStatus;
    public String paymentIdNo;
    public String paymentMode;
    public String paymentOrderNumber;
    public LocalDate fromDateDelivery;
    public LocalDate toDateDelivery;
    public LocalDate fromDateReceivedOn;
    public LocalDate toDateReceivedOn;
    public LocalDate fromDateShippedOn;
    public LocalDate toDateShippedOn;
    public Boolean isReturnRequestOpen;
    public Boolean isProductReturned;
    
}
