/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.payload;

import com.coms.model.ReturnStatus;
import com.coms.model.ReturnType;
import java.time.LocalDate;
import javax.validation.constraints.Positive;

/**
 *
 * @author z0043uwn
 */
public class ReturnRequestPayload {
    public String returnRequestId;
    public String customerId;
    public Boolean isAmountPaidToCustomer;
    public LocalDate fromDate;
    public LocalDate toDate;
    public ReturnStatus returnStatus;
    public ReturnType returnType;
    @Positive
    public Long soldProductId;
    public String productId;
    public String productName;
    public String orderNumber;
}
