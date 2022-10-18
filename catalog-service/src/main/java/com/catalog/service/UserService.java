/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.service;

import com.catalog.config.microservice.endpoint.UserServiceConfig;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author Priyank Agarwal
 */
@Service
public class UserService {

    @Autowired
    @Qualifier("userServiceWebClient")
    private WebClient webClient;

    @Autowired
    private UserServiceConfig userServiceConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public Boolean isAccountActive(String customerId) {
        Map<String, Object> mapResponse = webClient.get().uri(userServiceConfig.isAccountActive + customerId).retrieve()
                .bodyToMono(Map.class).block();
        if (mapResponse == null) {
            LOGGER.error("Something wrong with isAccountActive service.");
            return null;
        } else {
            return Boolean.parseBoolean(mapResponse.get("response").toString());
        }

    }
}
