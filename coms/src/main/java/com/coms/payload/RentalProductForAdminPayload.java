/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.payload;

import java.time.LocalDate;
import javax.validation.constraints.Positive;

/**
 *
 * @author z0043uwn
 */
public class RentalProductForAdminPayload extends RentalProductPayload{
    @Positive
    public Long purchasedFrom;
    public LocalDate fromDatePurchasedOn;
    public LocalDate toDatePurchasedOn;
}
