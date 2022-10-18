/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.service;

import com.coms.config.microservice.endpoint.UserServiceConfig;
import com.coms.dto.Customer;
import com.coms.exception.ResourceNotFoundException;
import com.coms.util.OrderServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author z0043uwn
 */
@Service
public class UserService {
    
    @Autowired
    @Qualifier("userServiceWebClient")
    private WebClient webClient;
    
    @Autowired
    private UserServiceConfig userServiceConfig;
    private static final Logger LOGGER = LoggerFactory.
            getLogger(UserService.class);
    
    public Customer getCustomerDetails(String customerId) {
        LOGGER.info("Getting customer details by id : {}", customerId);
        String responseAsString = webClient.get().uri(
                userServiceConfig.getCustomerDetailsEndpoint + customerId).
                retrieve().bodyToMono(String.class).block();
        if (responseAsString == null) {
            LOGGER.info("Customer is not found by id : {}", customerId);
            throw new ResourceNotFoundException(
                    "Customer",
                    customerId,
                    "Customer is not found");
        }
        return OrderServiceUtil.convertJsonToCustomerObj(responseAsString);
    }
    
    public String getCustomerEmailAddress(String customerId) {
        LOGGER.info("Getting customer email address for customerId : {}", customerId);
        String responseAsString = webClient.get().uri(
                userServiceConfig.getCustomerEmailAddressEndpoint + customerId).
                retrieve().bodyToMono(String.class).block();
        if (responseAsString == null) {
            throw new ResourceNotFoundException(
                    "Customer email address",
                    customerId,
                    "Customer email address is not found");
        }
        return OrderServiceUtil.retrieveEmailAddressFromJson(responseAsString);
    }
}
