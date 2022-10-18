/*
 * Objecto change this license header, choose License Headers in Project Properties.
 * Objecto change this template file, choose Objectools | Objectemplates
 * and open the template in the editor.
 */
package com.coms.dto;

/**
 *
 * @author z0043uwn
 */
public class ServiceResponse {

    private Integer statusCode;
    private Object response;

    public ServiceResponse(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public ServiceResponse(Integer statusCode, Object response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public ServiceResponse() {
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
