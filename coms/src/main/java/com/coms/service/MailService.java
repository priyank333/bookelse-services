/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.service;

import com.coms.config.app.App;
import com.coms.config.service.endpoint.MailServiceConfig;
import com.coms.controller.mgr.OrderControllerMgr;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author z0043uwn
 */
@Service
public class MailService {

    @Autowired
    @Qualifier("mailServiceWebClient")
    private WebClient mailWebClient;
    @Autowired
    private MailServiceConfig mailServiceConfig;
    private static final Logger LOGGER = LoggerFactory.
            getLogger(OrderControllerMgr.class);

    @Autowired
    private App app;

    public void sendMailForOrderConfirmation(
            Map<String, Object> mailBody) {
        LOGGER.info("Sending order confirmation mail");
        String str = mailWebClient.post().uri(
                mailServiceConfig.orderConfirmationEndpoint)
                .header("x-api-key", app.mailAPIKey).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                body(Mono.just(mailBody),
                        Map.class).retrieve().bodyToMono(String.class).block();
        LOGGER.info("Response :: {}", str);
    }

    public void sendShippingConfirmationMail(
            Map<String, Object> mailBody) {
        LOGGER.info("Sending shipping confirmation mail");
        String str = mailWebClient.post().uri(
                mailServiceConfig.shippingConfirmationEndpoint)
                .header("x-api-key", app.mailAPIKey).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                body(Mono.just(mailBody),
                        Map.class).retrieve().bodyToMono(String.class).block();
        LOGGER.info("Response :: {}", str);
    }

    public void sendDeliveryConfirmationMail(
            Map<String, Object> mailBody) {
        LOGGER.info("Sending delivery confirmation mail");
        String str = mailWebClient.post().uri(
                mailServiceConfig.deliveryConfirmationEndpoint).
                header("x-api-key", app.mailAPIKey).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                body(Mono.just(mailBody),
                        Map.class).retrieve().bodyToMono(String.class).block();
        LOGGER.info("Response :: {}", str);
    }
}
