/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

/**
 *
 * @author Priyank Agrawal
 */
public class ServiceResponse {

    private Integer statusCode;
    private Object response;

    public ServiceResponse() {
    }

    public ServiceResponse(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public ServiceResponse(Integer statusCode, Object response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" + "statusCode=" + statusCode + ", response=" + response + '}';
    }

}
