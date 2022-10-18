/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.repository;

import com.coms.config.sql.SqlQueries;
import com.coms.mapper.ReturnRequestMapper;
import com.coms.model.ReturnRequest;
import com.coms.model.ReturnStatus;
import com.coms.model.ReturnType;
import com.coms.payload.ReturnRequestPayload;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import static com.coms.repository.util.RepositoryUtil.*;
import java.time.LocalDate;
import java.util.Collections;
import org.springframework.stereotype.Repository;

/**
 *
 * @author z0043uwn
 */
@Repository
public class ReturnProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public List<ReturnRequest> listReturnRequest(
            ReturnRequestPayload returnRequestPayload) {
        String sqlQuery = sqlQueries.listAllReturnRequests.
                concat(generateWhereCondition(returnRequestPayload));
        List<ReturnRequest> returnRequestList = jdbcTemplate.query(
                sqlQuery,
                new ReturnRequestMapper());
        if (returnRequestList != null) {
            return returnRequestList;
        }
        return Collections.emptyList();
    }

    public String generateWhereCondition(
            ReturnRequestPayload returnRequestPayload) {
        StringBuilder whereCondition = new StringBuilder();
        String customerId = returnRequestPayload.customerId;
        String orderNumber = returnRequestPayload.orderNumber;
        String productId = returnRequestPayload.productId;
        String returnRequestId = returnRequestPayload.returnRequestId;
        ReturnStatus returnStatus = returnRequestPayload.returnStatus;
        ReturnType returnType = returnRequestPayload.returnType;
        Long soldProductId = returnRequestPayload.soldProductId;
        String productName = returnRequestPayload.productName;
        LocalDate fromDate = returnRequestPayload.fromDate;
        LocalDate toDate = returnRequestPayload.toDate;
        Boolean isAmountPaidToCustomer = returnRequestPayload.isAmountPaidToCustomer;
        if (customerId != null) {
            String trimmedValue = customerId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "rq", "customer_id",
                        whereCondition);
            }
        }
        if (orderNumber != null) {
            String trimmedValue = orderNumber.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "sp",
                        "order_number", whereCondition);
            }
        }
        if (productId != null) {
            String trimmedValue = productId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "sp", "product_id",
                        whereCondition);
            }
        }
        if (returnRequestId != null) {
            String trimmedValue = returnRequestId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "rq",
                        "return_request_id", whereCondition);
            }
        }
        if (returnStatus != null) {
            setValue(returnStatus.ordinal(), "rq",
                    "return_status", whereCondition);
        }
        if (returnType != null) {
            setValue(returnType.ordinal(), "rq",
                    "return_type", whereCondition);
        }
        if (soldProductId != null) {
            setValue(returnRequestPayload.soldProductId, "rq",
                    "sold_product_id", whereCondition);
        }
        if (productName != null) {
            String trimmedValue = productName.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "sp",
                        "product_name", whereCondition);
            }
        }
        if (fromDate != null && toDate != null) {
            setDateRange(fromDate,
                    toDate,
                    "rq", "requested_on", whereCondition);
        }
        if (isAmountPaidToCustomer != null) {
            setValue(returnRequestPayload.isAmountPaidToCustomer,
                    "rq", "is_amount_paid_to_customer", whereCondition);
        }
        return whereCondition.toString();
    }
}
