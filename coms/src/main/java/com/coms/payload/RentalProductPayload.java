/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.payload;

import java.time.LocalDate;

/**
 *
 * @author z0043uwn
 */
public class RentalProductPayload {
    public String orderNumber;
    public String customerId;
    public LocalDate fromDate;
    public LocalDate toDate;
    public String productName;
}
