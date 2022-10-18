/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.service;

import com.gateway.config.microservice.endpoint.UserServiceConfig;
import static com.gateway.constant.Constant.TOKEN_EXPIRATION_TIME;
import static com.gateway.constant.Constant.UNAUTHORIZED_RESPONSE_TOKEN_MSG;
import com.gateway.dto.TokenResponse;
import com.gateway.exception.OperationError;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author z0043uwn
 */
@Service
public class AuthService {

    @Autowired
    @Qualifier("userServiceWebClient")
    private WebClient webClient;

    @Autowired
    private UserServiceConfig userServiceConfig;

    @Autowired
    private TokenService tokenService;

    public TokenResponse customerAuthentication(String emailId, String password) {
        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("emailId", emailId);
        linkedMultiValueMap.add("password", password);
        HashMap<String, LinkedHashMap> response;
        response = webClient.post().uri(
                userServiceConfig.customerAuthenticationEndpoint)
                .body(BodyInserters.fromFormData(linkedMultiValueMap)).
                retrieve().onStatus(HttpStatus::is4xxClientError, res -> {
                    return res.bodyToMono(HashMap.class).flatMap(error -> {
                        linkedMultiValueMap.remove("password");
                        return Mono.error(
                                new OperationError(
                                        HttpStatus.UNAUTHORIZED,
                                        UNAUTHORIZED_RESPONSE_TOKEN_MSG,
                                        linkedMultiValueMap,
                                        "UserAuthentication"));
                    });
                }).bodyToMono(HashMap.class).block();
        String userType = "";
        String customerId = "";
        if (response != null && response.get("response") != null) {
            userType = response.get("response").get("userType").toString();
            customerId = response.get("response").
                    get("uniqueIdentification").toString();
        }
        String token = tokenService.getJWTToken(emailId);
        TokenResponse tokenResponse = new TokenResponse(
                token,
                LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME),
                TOKEN_EXPIRATION_TIME,
                HttpStatus.OK.value(),
                userType,
                customerId);
        return tokenResponse;
    }

    public TokenResponse adminAuthentication(String username, String password) {
        LinkedMultiValueMap<String, String> linkedMultiValueMap
                = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("username", username);
        linkedMultiValueMap.add("password", password);
        HashMap<String, LinkedHashMap> response;

        response = webClient.post().uri(
                userServiceConfig.adminAuthenticationEndpoint)
                .body(BodyInserters.fromFormData(linkedMultiValueMap)).
                retrieve().onStatus(HttpStatus::is4xxClientError, res -> {
                    return res.bodyToMono(HashMap.class).flatMap(error -> {
                        linkedMultiValueMap.remove("password");
                        return Mono.error(
                                new OperationError(
                                        HttpStatus.UNAUTHORIZED,
                                        UNAUTHORIZED_RESPONSE_TOKEN_MSG,
                                        linkedMultiValueMap,
                                        "UserAuthentication"));
                    });
                }).bodyToMono(HashMap.class).block();
        String userType = "";
        String adminId = "";
        if (response != null && response.get("response") != null) {
            userType = response.get("response").get("userType").toString();
            adminId = response.get("response").
                    get("uniqueIdentification").toString();
        }

        String token = tokenService.getJWTToken(adminId);
        TokenResponse tokenResponse = new TokenResponse(
                token,
                LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME),
                TOKEN_EXPIRATION_TIME,
                HttpStatus.OK.value(),
                userType,
                adminId);
        return tokenResponse;
    }
}
