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
public class Card {

    @JsonProperty("id")
    private String id;
    @JsonProperty("entity")
    private String entity;
    @JsonProperty("name")
    private String name;
    @JsonProperty("last4")
    private String last4;
    @JsonProperty("network")
    private String network;
    @JsonProperty("type")
    private String type;
    @JsonProperty("issuer")
    private String issuer;
    @JsonProperty("international")
    private Boolean international;
    @JsonProperty("emi")
    private Boolean emi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Boolean getInternational() {
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }

    public Boolean getEmi() {
        return emi;
    }

    public void setEmi(Boolean emi) {
        this.emi = emi;
    }

    @Override
    public String toString() {
        return "Card{" + "id=" + id + ", entity=" + entity + ", name=" + name + ", last4=" + last4 + ", network=" + network + ", type=" + type + ", issuer=" + issuer + ", international=" + international + ", emi=" + emi + '}';
    }

}
