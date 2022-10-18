/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.config.auth.jwt;

/**
 *
 * @author z0043uwn
 */
public class AllowedRequest {

    public final static String[] OPEN_REQUESTS = {
        "/actuator/health",
        "/app/admin/v1/login",
        "/app/customer/v1/login",
        "/user-service/authentication/customer",
        "/user-service/authentication/admin",
        "/user-service/otp-service/v1/send-otp-in-mail",
        "/user-service/customer/v1/register",
        "/user-service/customer/v1/email-verification",
        "/user-service/customer/v1/reset-password",
        "/user-service/area/v1/list-area/**",
        "/user-service/area/v1/is-pincode-exist",
        "/user-service/area/v1/list-area/city/**",
        "/user-service/state/v1/list-state/**",
        "/user-service/city/v1/list-cities/state/**",
        "/user-service/city/v1/list-city/**",
        "/user-service/customer-support/v1/capture-customer-query",
        "/catalog-service/category/v1/list-category/**",
        "/catalog-service/university/v1/list-university/**",
        "/catalog-service/course/v1/list-course/**",
        "/catalog-service/product/v1/list-product",
        "/catalog-service/product-image/v1/list-image/product/**",
        "/order-service/payment/v1/update-payment-status",
        "/actuator/**"
    };
}
