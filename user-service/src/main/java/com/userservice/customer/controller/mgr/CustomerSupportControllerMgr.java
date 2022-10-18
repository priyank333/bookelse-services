/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.google.common.collect.Lists;
import com.userservice.customer.dao.CustomerQueryDao;
import com.userservice.customer.model.CustomerQuery;
import com.userservice.model.ServiceResponse;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class CustomerSupportControllerMgr {

    @Autowired
    private CustomerQueryDao customerQueryDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSupportControllerMgr.class);

    public ServiceResponse captureCustomerQuery(CustomerQuery customerQuery) {
        formatInput(customerQuery);
        customerQuery = customerQueryDao.save(customerQuery);
        LOGGER.info("Added Response Id : {}", customerQuery.getQueryId());
        LinkedHashMap<String, Object> responseValues = new LinkedHashMap<>();
        responseValues.put("queryId", customerQuery.getQueryId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseValues);
    }

    private void formatInput(CustomerQuery customerQuery) {
        customerQuery.setCapturedOn(LocalDateTime.now());
    }

    public ServiceResponse listCustomerQueries() {
        List<CustomerQuery> queries = customerQueryDao.findAllByOrderByCapturedOnDesc();
        if (null != queries && !queries.isEmpty()) {
            List<CustomerQuery> queryList = Lists.newArrayList(queries);
            return new ServiceResponse(HttpStatus.OK.value(), queryList);
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    public ServiceResponse listCustomerQueriesById(Long queryId) {
        Optional<CustomerQuery> query = customerQueryDao.findById(queryId);
        LOGGER.info("Listing query by id :: {}", queryId);
        if (query.isPresent()) {
            LOGGER.info("Listing query by id :: {} :: value :: {}", queryId, query.get());
            return new ServiceResponse(HttpStatus.OK.value(), query.get());
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }
}
