/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.services;

import com.jsoniter.JsonIterator;
import com.userservice.config.MailServiceConfig;
import com.userservice.config.ServiceConfig;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class MailService {

        @Autowired
        @Qualifier("mailServiceWebClient")
        private WebClient mailWebClient;
        @Autowired
        private MailServiceConfig mailServiceConfig;
        private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
        @Autowired
        private ServiceConfig serviceConfig;

        public Boolean sendOTPInMail(String firstName, String customerEmailAddress, Map<String, String> otpDescription,
                        String otp) {
                String[] toAddressList = new String[] { customerEmailAddress };
                Map<String, Object> requestBody = new HashMap<>();
                Map<String, Object> mailInfo = new HashMap<>();
                mailInfo.put("mailSubject", otpDescription.get("subject"));
                mailInfo.put("toAddressList", toAddressList);
                requestBody.put("firstName", firstName);
                requestBody.put("message", otpDescription.get("message"));
                requestBody.put("otpDescription", otpDescription.get("otpDescription"));
                requestBody.put("otp", otp);
                requestBody.put("mailInfo", mailInfo);
                String jsonResponse = mailWebClient.post().uri(mailServiceConfig.sendOTPEndpoint)
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .header("x-api-key", serviceConfig.mailAPIKey).body(Mono.just(requestBody), Map.class)
                                .retrieve().bodyToMono(String.class).block();
                Integer statusCode = JsonIterator.deserialize(jsonResponse).get("statusCode").as(Integer.class);
                LOGGER.info("IsOTP sent to emailId : {}", customerEmailAddress);
                return statusCode == HttpStatus.OK.value();
        }
}
