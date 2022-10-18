/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.config.microservice.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author z0043uwn
 */
@Component
@PropertySource("classpath:config/microservice/endpoint/user-service-endpoint.properties")
public class UserServiceConfig {

    @Value("${user-service.getCustomerDetails}")
    public String getCustomerDetailsEndpoint;
    @Value("${user-service.getCustomerEmailAddress}")
    public String getCustomerEmailAddressEndpoint;

}
