/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Priyank Agrawal
 */
public class OperationError extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;
    private Object resource;
    private String moduleName;

    public OperationError() {
    }

    public OperationError(HttpStatus httpStatus, String message, String moduleName) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.moduleName = moduleName;
    }

    public OperationError(HttpStatus httpStatus, String message, Object resource, String moduleName) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.resource = resource;
        this.moduleName = moduleName;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
        return "OperationError{" + "httpStatus=" + httpStatus + ", message=" + message + ", resource=" + resource
                + ", moduleName=" + moduleName + '}';
    }

}
