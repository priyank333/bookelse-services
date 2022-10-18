/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Priyank Agrawal
 */
public class UserInfo {

    public String userType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String uniqueIdentification;

    public UserInfo() {
    }

    public UserInfo(String userType, String uniqueIdentification) {
        this.userType = userType;
        this.uniqueIdentification = uniqueIdentification;
    }

    public UserInfo(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUniqueIdentification() {
        return uniqueIdentification;
    }

    public void setUniqueIdentification(String uniqueIdentification) {
        this.uniqueIdentification = uniqueIdentification;
    }

    @Override
    public String toString() {
        return "UserInfo{" + "userType=" + userType + ", uniqueIdentification=" + uniqueIdentification + '}';
    }

}
