/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.config.service.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author z0043uwn
 */
@Component
@PropertySource("classpath:config/service/endpoint/service-endpoint.properties")
public class MailServiceConfig {

    @Value("${mail.order-confirmation}")
    public String orderConfirmationEndpoint;

    @Value("${mail.shipping-confirmation}")
    public String shippingConfirmationEndpoint;
    
    @Value("${mail.delivery-confirmation}")
    public String deliveryConfirmationEndpoint;

}
