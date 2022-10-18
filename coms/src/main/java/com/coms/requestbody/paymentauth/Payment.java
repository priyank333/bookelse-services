/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.requestbody.paymentauth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author z0043uwn
 */
public class Payment {

    @JsonProperty("entity")
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "Payment{" + "entity=" + entity + '}';
    }

}
