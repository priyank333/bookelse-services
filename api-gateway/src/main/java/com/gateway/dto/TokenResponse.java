/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;

/**
 *
 * @author z0043uwn
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TokenResponse {

    private String token;
    private LocalDateTime expiredAt;
    private Integer tokenValidity;
    private Integer response;
    private String message;
    private String uniqueIdentificationId;

    public TokenResponse(String token, 
            LocalDateTime expiredAt,
            Integer tokenValidity,
            Integer response,
            String message,
            String uniqueIdentificationId) {
        this.token = token;
        this.expiredAt = expiredAt;
        this.tokenValidity = tokenValidity;
        this.response = response;
        this.message = message;
        this.uniqueIdentificationId = uniqueIdentificationId;
    }

    public TokenResponse(Integer response, String message) {
        this.response = response;
        this.message = message;
    }

    public TokenResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Integer getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(Integer tokenValidity) {
        this.tokenValidity = tokenValidity;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUniqueIdentificationId() {
        return uniqueIdentificationId;
    }

    public void setUniqueIdentificationId(String uniqueIdentificationId) {
        this.uniqueIdentificationId = uniqueIdentificationId;
    }

    @Override
    public String toString() {
        return "TokenResponse{" + "token=" + token 
                + ", expiredAt=" + expiredAt 
                + ", tokenValidity=" + tokenValidity 
                + ", response=" + response 
                + ", message=" + message 
                + ", uniqueIdentificationId=" + uniqueIdentificationId + '}';
    }

}
