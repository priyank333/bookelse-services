/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.config.service;

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
@PropertySource("classpath:config/service/service-config.properties")
public class ServiceConfig {

    @Value("${mail.service.host}")
    public String mailServiceHost;

    @Bean(name = "mailServiceWebClient")
    public WebClient getMailServiceWebClient() {
        return WebClient.builder().
                baseUrl(mailServiceHost).build();
    }
}
