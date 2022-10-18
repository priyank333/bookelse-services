/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

/**
 *
 * @author Priyank Agrawal
 */
public class ExceptionResponse {

    private ApiError apiError;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object resource;

    public ExceptionResponse() {
    }

    public ExceptionResponse(ApiError apiError) {
        this.apiError = apiError;
    }

    public ExceptionResponse(ApiError apiError, Object resource) {
        this.apiError = apiError;
        this.resource = resource;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" + "apiError=" + apiError + ", resource=" + resource + '}';
    }

}
