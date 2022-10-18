/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.requestbody.paymentauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author z0043uwn
 */
public class PaymentPayload {

    @JsonProperty("entity")
    private String entity;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("event")
    private String event;
    @JsonProperty("contains")
    private List<String> contains;
    @JsonProperty("payload")
    private Payload payload;
    @JsonProperty("created_at")
    private Long createdAt;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public List<String> getContains() {
        return contains;
    }

    public void setContains(List<String> contains) {
        this.contains = contains;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PaymentAuthorized{" + "entity=" + entity + ", accountId=" + accountId + ", event=" + event + ", contains=" + contains + ", payload=" + payload + ", createdAt=" + createdAt + '}';
    }

}
