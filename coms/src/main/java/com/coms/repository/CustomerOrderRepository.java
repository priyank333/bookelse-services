/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.repository;

import com.coms.config.sql.SqlQueries;
import com.coms.mapper.CustomerOrderMapper;
import com.coms.model.CustomerOrder;
import com.coms.model.OrderStatus;
import com.coms.payload.CustomerOrderForAdminPayload;
import com.coms.payload.CustomerOrderPayload;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.coms.repository.util.RepositoryUtil.*;
import java.time.LocalDate;

/**
 *
 * @author z0043uwn
 */
@Repository
public class CustomerOrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public List<CustomerOrder> listCustomerOrders(
            CustomerOrderPayload orderPayload) {
        String sqlQuery = sqlQueries.listAllCustomerOrders.
                concat(generateWhereCondition(orderPayload));
        Map<String, CustomerOrder> customerOrders = jdbcTemplate.query(
                sqlQuery,
                new CustomerOrderMapper());
        if (customerOrders != null) {
            Collection<CustomerOrder> productCollection = customerOrders.values();
            return productCollection.stream().collect(toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    public List<CustomerOrder> listCustomerOrders(
            CustomerOrderForAdminPayload orderPayload) {
        StringBuilder sqlQuery = new StringBuilder(
                sqlQueries.listAllCustomerOrdersForAdmin);

        sqlQuery.append(generateWhereCondition(orderPayload)).
                append(generateWhereConditionForAdmin(orderPayload));
        Map<String, CustomerOrder> customerOrders = jdbcTemplate.query(
                sqlQuery.toString(),
                new CustomerOrderMapper());
        if (customerOrders != null) {
            Collection<CustomerOrder> productCollection = customerOrders.values();
            return productCollection.stream().collect(toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    private String generateWhereCondition(CustomerOrderPayload orderPayload) {
        StringBuilder whereCondition = new StringBuilder();
        String customerId = orderPayload.customerId;
        Boolean isAmountPaid = orderPayload.isAmountPaid;
        String orderNumber = orderPayload.orderNumber;
        OrderStatus orderStatus = orderPayload.orderStatus;
        String paymentIdNo = orderPayload.paymentIdNo;
        String paymentOrderNumber = orderPayload.paymentOrderNumber;
        String paymentMode = orderPayload.paymentMode;
        LocalDate fromDateReceivedOn = orderPayload.fromDateReceivedOn;
        LocalDate toDateReceivedOn = orderPayload.toDateReceivedOn;
        LocalDate fromDateDelivery = orderPayload.fromDateDelivery;
        LocalDate toDateDelivery = orderPayload.toDateDelivery;
        LocalDate fromDateShippedOn = orderPayload.fromDateShippedOn;
        LocalDate toDateShippedOn = orderPayload.toDateShippedOn;
        Boolean isProductReturned = orderPayload.isProductReturned;
        Boolean isReturnRequestOpen = orderPayload.isReturnRequestOpen;
        if (customerId != null) {
            String trimmedValue = customerId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "co", "customer_id",
                        whereCondition);
            }
        }
        if (isAmountPaid != null) {
            setValue(isAmountPaid, "co", "is_amount_paid",
                    whereCondition);
        }
        if (orderNumber != null) {
            String trimmedValue = orderNumber.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "co", "order_number", whereCondition);
            }
        }

        if (orderStatus != null) {
            setValue(orderStatus.ordinal(), "co", "order_status",
                    whereCondition);
        }
        if (paymentIdNo != null) {
            String trimmedValue = paymentIdNo.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "co", "payment_id_no", whereCondition);
            }
        }
        if (paymentOrderNumber != null) {
            String trimmedValue = paymentOrderNumber.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "co", "payment_order_number",
                        whereCondition);
            }
        }
        if (paymentMode != null) {
            String trimmedValue = paymentMode.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "co", "payment_mode", whereCondition);
            }
        }

        if (fromDateReceivedOn != null && toDateReceivedOn != null) {
            setDateRange(fromDateReceivedOn,
                    toDateReceivedOn, "co", "received_on",
                    whereCondition);
        }
        if (fromDateDelivery != null && toDateDelivery != null) {
            setDateRange(fromDateDelivery, toDateDelivery,
                    "co", "delivered_on", whereCondition);
        }
        if (fromDateShippedOn != null && toDateShippedOn != null) {
            setDateRange(fromDateShippedOn, toDateShippedOn,
                    "co", "shipped_on", whereCondition);
        }
        if (isProductReturned != null) {
            setValue(isProductReturned, "sp", "is_product_returned",
                    whereCondition);
        }
        if (isReturnRequestOpen != null) {
            setValue(isReturnRequestOpen, "sp", "is_return_request_open",
                    whereCondition);
        }

        return whereCondition.toString();

    }

    private String generateWhereConditionForAdmin(
            CustomerOrderForAdminPayload orderPayload) {
        StringBuilder whereCondition = new StringBuilder();
        String stockItemId = orderPayload.stockItemId;
        String purchasedFrom = orderPayload.purchasedFrom;
        if (stockItemId != null) {
            String trimmedValue = stockItemId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "sp", "stock_item_id",
                        whereCondition);
            }
        }
        if (purchasedFrom != null) {
            String trimmedValue = purchasedFrom.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "sp", "purchased_from",
                        whereCondition);
            }
        }
        return whereCondition.toString();

    }

}
