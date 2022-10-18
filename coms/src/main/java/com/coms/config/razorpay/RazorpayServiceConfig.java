/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.config.razorpay;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author z0043uwn
 */
@Component
@PropertySource("classpath:config/razorpay/razorpay-config.properties")
public class RazorpayServiceConfig {

    @Value("${razorpay.key_id}")
    public String keyId;

    @Value("${razorpay.key_secret}")
    public String keySecret;

    @Value("${razorpay.payment_secret_key}")
    public String paymentSecretKey;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(
            RazorpayServiceConfig.class);

    @Bean
    public RazorpayClient getRazorpayClient() {
        RazorpayClient razorpayClient = null;
        try {
            razorpayClient = new RazorpayClient(keyId, keySecret);
        } catch (RazorpayException ex) {
            LOGGER.error(ex.getMessage());
        }
        return razorpayClient;
    }
}
