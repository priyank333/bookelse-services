/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.cron;

import com.coms.controller.mgr.RentalProductControllerMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author z0043uwn
 */
@Component
public class ProcessDelayedReturnCharge {

    @Autowired
    private RentalProductControllerMgr rentalProductControllerMgr;

    @Scheduled(cron = "0 0 00 * * ?")
    public void processDelayedCharges() {
        rentalProductControllerMgr.processDelayedRentalProductCharges();
    }
}
