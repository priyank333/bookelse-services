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
@PropertySource("classpath:config/microservice/endpoint/catalog-service-endpoint.properties")
public class ProductServiceConfig {

    @Value("${product-service.getDraft}")
    public String getDraftEndpoint;
    @Value("${product-service.isQuantityAvailable}")
    public String isQuantityAvailableEndpoint;
    @Value("${product-service.reserveQuantity}")
    public String reserveQuantityEndpoint;
    @Value("${product-service.releaseQuantity}")
    public String releaseQuantityEndpoint;
    @Value("${product-service.dedutQuantity}")
    public String dedutQuantityEndpoint;
    @Value("${product-service.deleteDraft}")
    public String deleteDraft;
    @Value("${product-service.updateStock}")
    public String updateStock;
}
