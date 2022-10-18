/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.config.microservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author Priyank Agrawal
 */
@Component
@PropertySource("classpath:config/microservice/microservices-config.properties")
public class MicroserviceConfig {

    @Value("${user-service.host}")
    public String userServiceHost;

    @Bean(name = "userServiceWebClient")
    public WebClient getUserServiceWebClient() {
        return WebClient.builder().baseUrl(userServiceHost).build();
    }
}
