/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.service;

import static com.gateway.constant.Constant.PREFIX;
import static com.gateway.constant.Constant.SECRET;
import static com.gateway.constant.Constant.TOKEN_EXPIRATION_TIME;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author z0043uwn
 */
@Service
public class TokenService {

    private static final Logger LOGGER = LoggerFactory.
            getLogger(TokenService.class);

    public String getJWTToken(String username) {
        String secretKey = SECRET;
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(
                        System.currentTimeMillis()
                        + TimeUnit.MINUTES.toMillis(TOKEN_EXPIRATION_TIME)))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return PREFIX + token;
    }
}
