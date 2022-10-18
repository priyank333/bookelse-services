/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

/**
 *
 * @author z0043uwn
 */
public class ApiError {

    private HttpStatus statusCode;
    private LocalDateTime dateTime;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object errors;

    public ApiError() {
    }

    public ApiError(HttpStatus statusCode, LocalDateTime dateTime, String message) {
        this.statusCode = statusCode;
        this.dateTime = dateTime;
        this.message = message;
    }

    public ApiError(HttpStatus statusCode, LocalDateTime dateTime, String message, Object errors) {
        this.statusCode = statusCode;
        this.dateTime = dateTime;
        this.message = message;
        this.errors = errors;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus status) {
        this.statusCode = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ApiError{" + "statusCode=" + statusCode + ", dateTime=" + dateTime + ", message=" + message + ", errors=" + errors + '}';
    }

}
