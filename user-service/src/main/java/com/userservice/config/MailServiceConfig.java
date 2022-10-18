/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Priyank Agrawal
 */
@Component
@PropertySource("classpath:services/endpoints/service-endpoints.properties")
public class MailServiceConfig {

    @Value("${mail.send-otp}")
    public String sendOTPEndpoint;
}
