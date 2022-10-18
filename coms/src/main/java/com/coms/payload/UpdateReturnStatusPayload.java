/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.payload;

import com.coms.model.ReturnStatus;

/**
 *
 * @author z0043uwn
 */
public class UpdateReturnStatusPayload {

    public Boolean updateInventory;
    public String returnRequestId;
    public ReturnStatus returnStatus;
}
