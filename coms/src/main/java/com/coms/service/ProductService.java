/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.service;

import com.coms.config.microservice.endpoint.ProductServiceConfig;
import com.coms.dto.Draft;
import com.coms.dto.StockItem;
import com.coms.exception.OperationError;
import com.coms.exception.ResourceNotFoundException;
import com.coms.util.OrderServiceUtil;
import static com.coms.util.OrderServiceUtil.jsonToStockItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author z0043uwn
 */
@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.
            getLogger(ProductService.class);
    @Autowired
    @Qualifier("productServiceWebClient")
    private WebClient webClient;

    @Autowired
    private ProductServiceConfig productServiceConfig;

    public Draft getDraft(Long draftId) {
        LOGGER.info("Getting draft by id : {}", draftId);
        String responseAsString = webClient.get().uri(
                productServiceConfig.getDraftEndpoint + draftId).
                retrieve().bodyToMono(String.class).block();
        if (responseAsString == null) {
            LOGGER.info("Draft is not found by id : {}", draftId);
            throw new ResourceNotFoundException(
                    "Draft",
                    draftId,
                    "Draft is not found.");
        }
        return OrderServiceUtil.convertJsonToDraft(responseAsString);
    }

    public Boolean isQuantityAvailable(String productId, Integer quantity) {
        LOGGER.info("Calling isQuantityAvailable service for "
                + "product {} X {} quantity", productId, quantity);
        String fullEndpointURI = new StringBuilder(
                productServiceConfig.isQuantityAvailableEndpoint).
                append("?productId=").
                append(productId).
                append("&quantity=").
                append(quantity).toString();
        String responseAsString = webClient.get().uri(
                fullEndpointURI).retrieve().bodyToMono(String.class).block();
        if (responseAsString == null) {
            Map<String, Object> requestedResource = new HashMap<>();
            requestedResource.put("productId", productId);
            requestedResource.put("quantity", quantity);
            throw new ResourceNotFoundException(
                    "Quantity Validation",
                    requestedResource,
                    "Quantity validation of product is not able to execute.");
        }
        return OrderServiceUtil.convertJSONToBoolean(responseAsString);
    }

    public Boolean reserveQuantity(Map<String, Integer> productList) {
        LOGGER.info("Reserving quantity : {}", productList);
        String fullEndpointURI = productServiceConfig.reserveQuantityEndpoint;
        String responseAsString = webClient.patch().uri(
                fullEndpointURI).body(
                        BodyInserters.fromPublisher(
                                Mono.just(productList), Map.class)).
                retrieve().bodyToMono(String.class).block();
        if (responseAsString == null) {
            throw new ResourceNotFoundException(
                    "Reserving Quantity",
                    productList,
                    "Not able to reserve quantity of product.");
        }
        return OrderServiceUtil.convertJSONToBoolean(responseAsString);
    }

    public Boolean releaseQuantity(Map<String, Integer> productList) {
        LOGGER.info("Releasing quantity : {}", productList);
        String fullEndpointURI = productServiceConfig.releaseQuantityEndpoint;
        String responseAsString = webClient.patch().uri(
                fullEndpointURI).body(BodyInserters.fromPublisher(
                        Mono.just(productList), Map.class)).
                retrieve().bodyToMono(String.class).block();
        if (responseAsString == null) {
            throw new ResourceNotFoundException(
                    "Releasing Quantity",
                    productList,
                    "Not able to release quantity of product.");
        }
        return OrderServiceUtil.convertJSONToBoolean(responseAsString);
    }

    public List<StockItem> deductStock(
            String productId, Integer quantity) {
        LOGGER.info("Calling deductProduct service to deduct product "
                + "from inventory for product : {} X {} quantity",
                productId,
                quantity);
        String fullEndpointURI = new StringBuilder(
                productServiceConfig.dedutQuantityEndpoint).
                append("?productId=").
                append(productId).
                append("&quantity=").
                append(quantity).toString();
        String responseAsString = webClient.put().uri(
                fullEndpointURI).retrieve().
                bodyToMono(String.class).block();
        if (responseAsString == null) {
            Map<String, Object> requestedResource = new HashMap<>();
            requestedResource.put("productId", productId);
            requestedResource.put("quantity", quantity);
            throw new ResourceNotFoundException(
                    "Quantity Deduction",
                    requestedResource,
                    "Requested resource is not found.");
        }
        return jsonToStockItems(
                responseAsString);
    }

    public Boolean deleteDraftByCustomer(String customerId) {
        LOGGER.info("Deleting draft for customer : {}", customerId);
        String fullEndpointURI = new StringBuilder(
                productServiceConfig.deleteDraft).
                append("?customerId=").
                append(customerId).toString();
        String responseAsString = webClient.delete().uri(
                fullEndpointURI).retrieve().
                bodyToMono(String.class).block();
        if (responseAsString == null) {
            throw new ResourceNotFoundException(
                    "Draft",
                    customerId,
                    "Draft is not found by customer.");
        }
        return OrderServiceUtil.convertJSONToBoolean(
                responseAsString);
    }

    public Boolean updateStock(
            Long stockItemId,
            String productId,
            Boolean status,
            Boolean updateQuantity) {
        LOGGER.info("Updating stock for { stockItemId: {}, productId: {}",
                stockItemId,
                productId);
        String fullEndpointURI = new StringBuilder(
                productServiceConfig.updateStock).toString();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("stockItemList", Arrays.asList(stockItemId));
        requestBody.put("isProductSold", status);
        requestBody.put("productId", productId);
        requestBody.put("updateQuantity", updateQuantity);

        return Boolean.valueOf(
                webClient.patch().uri(
                        fullEndpointURI)
                        .bodyValue(requestBody).
                        retrieve().onStatus(HttpStatus::is4xxClientError, res -> {
                            return res.bodyToMono(HashMap.class).flatMap(error -> {
                                ObjectMapper objectMapper = new ObjectMapper();
                                Map<String, Object> errorResp = objectMapper.
                                        convertValue(error.get("apiError"),
                                                Map.class);
                                return Mono.error(new OperationError(
                                        HttpStatus.valueOf(errorResp.get("statusCode").
                                                toString()),
                                        errorResp.get("message").toString(), "UpdateStock"));
                            });
                        }).bodyToMono(HashMap.class).block().get("response").toString());
    }

}
