/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.config.microservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author z0043uwn
 */
@Component
@PropertySource("classpath:config/microservice/microservices-config.properties")
public class MicroserviceConfig {

    @Value("${product-service.host}")
    public String productServiceHost;
    @Value("${user-service.host}")
    public String userServiceHost;

    @Bean(name = "userServiceWebClient")
    public WebClient getUserServiceWebClient() {
        return WebClient.builder().
                baseUrl(userServiceHost).build();

    }

    @Bean(name = "productServiceWebClient")
    public WebClient getProductServiceWebClient() {
        return WebClient.builder().
                baseUrl(productServiceHost).build();
    }
}
