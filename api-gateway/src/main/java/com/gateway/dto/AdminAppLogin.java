/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.dto;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author z0043uwn
 */
public class AdminAppLogin {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public AdminAppLogin() {
    }

    public AdminAppLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminAppLogin{" + "username=" + username + ", password=" + password + '}';
    }

}
