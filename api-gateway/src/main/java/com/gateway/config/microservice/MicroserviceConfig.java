/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.config.microservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author Priyank Agrawal
 */
@Configuration
@PropertySource("classpath:com/gateway/config/microservice/services-config.properties")
public class MicroserviceConfig {

    @Value("${user-service.host}")
    public String userServiceHost;

    @Bean(name = "userServiceWebClient")
    public WebClient getUserServiceWebClient() {
        return WebClient.builder().
                baseUrl(userServiceHost).build();
    }
}
