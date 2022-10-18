/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.config;

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
@PropertySource("classpath:services/service-config.properties")
public class ServiceConfig {

    @Value("${mail.service.host}")
    public String mailServiceHost;
    @Value("${mail.APIKey}")
    public String mailAPIKey;

    @Bean(name = "mailServiceWebClient")
    public WebClient getMailServiceWebClient() {
        return WebClient.builder().baseUrl(mailServiceHost).build();
    }
}
