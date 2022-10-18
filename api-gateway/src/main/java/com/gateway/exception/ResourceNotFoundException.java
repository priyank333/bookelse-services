/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.exception;

/**
 *
 * @author z0043uwn
 */
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private Object requestedValue;
    private String message;

    public ResourceNotFoundException(
            String resourceName,
            Object requestedValue,
            String message) {
        super(message);
        this.resourceName = resourceName;
        this.requestedValue = requestedValue;
        this.message = message;
    }

    
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Object getRequestedValue() {
        return requestedValue;
    }

    public void setRequestedValue(Object requestedValue) {
        this.requestedValue = requestedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResourceNotFoundException{" + "resourceName=" + resourceName + ", requestedValue=" + requestedValue + ", message=" + message + '}';
    }

}
