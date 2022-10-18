/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author z0043uwn
 */
public class CustomerAppLogin {

    @NotBlank
    @Email
    private String emailId;
    @NotBlank
    private String password;

    public CustomerAppLogin() {
    }

    public CustomerAppLogin(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CustomerAppLogin{" + "emailId=" + emailId + ", password=" + password + '}';
    }

}
