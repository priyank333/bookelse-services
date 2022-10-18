/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.config.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author z0043uwn
 */
@Component
@PropertySource("classpath:config/app/property/app.properties")
public class App {

    @Value("${mail.subject.deliveryConfirmation}")
    public String deliveryConfirmationMailSub;

    @Value("${mail.subject.shippingConfirmation}")
    public String shippingConfirmationMailSub;

    @Value("${mail.subject.orderConfirmation}")
    public String orderConfirmationMailSub;

    @Value("${mail.service.apiKey}")
    public String mailAPIKey;
}
