/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.config.auth.jwt;

import static com.gateway.config.auth.jwt.AllowedRequest.OPEN_REQUESTS;
import com.gateway.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author z0043uwn
 */
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()
                .addFilterAfter(
                        new JWTAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(OPEN_REQUESTS).permitAll()
                .anyRequest().authenticated();
    }
}
