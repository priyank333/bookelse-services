/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.config.microservice.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Priyank Agrawal
 */
@Component
@PropertySource("classpath:com/gateway/config/microservice/endpoint/user-service-endpoint.properties")
public class UserServiceConfig {

    @Value("${user-service.customer.authentication}")
    public String customerAuthenticationEndpoint;

    @Value("${user-service.admin.authentication}")
    public String adminAuthenticationEndpoint;

}
