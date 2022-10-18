/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.mapper;

import com.coms.model.CustomerOrder;
import com.coms.model.OrderStatus;
import com.coms.model.PaymentStatus;
import com.coms.model.SoldProduct;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author z0043uwn
 */
public class CustomerOrderMapper implements ResultSetExtractor<Map<String, CustomerOrder>> {

    private Map<String, Boolean> columnsInResultSet;

    @Override
    public Map<String, CustomerOrder> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        setColumns(rs);
        Map<String, CustomerOrder> customerOrderMap = new HashMap<>();
        while (rs.next()) {
            String orderNumber = rs.getString("order_number");
            CustomerOrder customerOrder = customerOrderMap.get(orderNumber);
            if (customerOrder == null) {
                customerOrder = new CustomerOrder(orderNumber);
                customerOrderMap.put(orderNumber, customerOrder);
                customerOrder.setCustomerId(rs.getString("customer_id"));
                customerOrder.setFinalAmount(rs.getDouble("final_amount"));
                customerOrder.setIsAmountPaid(rs.getBoolean("is_amount_paid"));
                customerOrder.setOrderStatus(OrderStatus.values()[rs.getInt("order_status")]);
                customerOrder.setOrderedDate(rs.getTimestamp("ordered_date"));
                customerOrder.setPaymentIdNo(rs.getString("payment_id_no"));
                customerOrder.setPaymentMode(rs.getString("payment_mode"));
                customerOrder.setPaymentOrderNumber(rs.getString("payment_order_number"));
                customerOrder.setBillingAddress(rs.getString("billing_address"));
                customerOrder.setShippingAddress(rs.getString("shipping_address"));
                customerOrder.setShippingCharge(
                        rs.getString("shipping_charge") == null
                        ? 0.00
                        : Double.parseDouble(rs.getString("shipping_charge")));
                Date deliveredOn = rs.getDate("delivered_on");
                Date receivedOn = rs.getDate("received_on");
                Date shippedOn = rs.getDate("shipped_on");
                if (deliveredOn != null) {
                    customerOrder.setDeliveredOn(deliveredOn.toLocalDate());
                }
                if (receivedOn != null) {
                    customerOrder.setReceivedOn(receivedOn.toLocalDate());
                }
                if (shippedOn != null) {
                    customerOrder.setShippedOn(shippedOn.toLocalDate());
                }
                if (columnsInResultSet.get("auth_code") != null) {
                    customerOrder.setAuthCode(rs.getString("auth_code"));
                }
                if (columnsInResultSet.get("bank_transaction_id") != null) {
                    customerOrder.setBankTransactionId(
                            rs.getString("bank_transaction_id"));
                }
                if (columnsInResultSet.get("payment_error_code") != null) {
                    customerOrder.setPaymentErrorCode(
                            rs.getString("payment_error_code"));
                }
                if (columnsInResultSet.get("payment_error_description") != null) {
                    customerOrder.setPaymentErrorDescription(
                            rs.getString("payment_error_description"));
                }
                if (columnsInResultSet.get("payment_error_reason") != null) {
                    customerOrder.setPaymentErrorReason(
                            rs.getString("payment_error_reason"));
                }
                if (columnsInResultSet.get("payment_error_source") != null) {
                    customerOrder.setPaymentErrorSource(
                            rs.getString("payment_error_source"));
                }
                if (columnsInResultSet.get("payment_error_step") != null) {
                    customerOrder.setPaymentErrorStep(
                            rs.getString("payment_error_step"));
                }
                if (columnsInResultSet.get("payment_status") != null) {
                    customerOrder.setPaymentStatus(
                            PaymentStatus.values()[rs.getInt("payment_status")]);
                }
                if (columnsInResultSet.get("rrn") != null) {
                    customerOrder.setRrn(rs.getString("rrn"));
                }
                if (columnsInResultSet.get("upi_transaction_id") != null) {
                    customerOrder.setUpiTransactionId(
                            rs.getString("upi_transaction_id"));
                }
                customerOrder.setProductsList(new ArrayList<>());
            }
            SoldProduct soldProduct = new SoldProduct(rs.getLong("sold_product_id"));
            soldProduct.setDiscount(rs.getDouble("discount"));
            soldProduct.setIsProductOnRent(rs.getBoolean("is_product_on_rent"));
            soldProduct.setMaxDayForReturn(rs.getInt("max_day_for_return"));
            soldProduct.setProductId(rs.getString("product_id"));
            soldProduct.setSellPrice(rs.getDouble("sell_price"));
            soldProduct.setProductName(rs.getString("product_name"));
            soldProduct.setRemarks(rs.getString("remarks"));
            if (columnsInResultSet.get("purchased_on") != null) {
                Date purchasedOn = rs.getDate("purchased_on");
                if (purchasedOn != null) {
                    soldProduct.setPurchasedOn(purchasedOn.toLocalDate());
                }
            }
            if (columnsInResultSet.get("purchased_price") != null) {
                Double purchasedPrice = rs.getDouble("purchased_price");
                soldProduct.setPurchasedPrice(purchasedPrice);
            }

            if (columnsInResultSet.get("purchased_from") != null) {
                Long purchasedFrom = rs.getLong("purchased_from");
                soldProduct.setPurchasedFrom(purchasedFrom);
            }
            if (columnsInResultSet.get("stock_item_id") != null) {
                soldProduct.setStockItemId(
                        rs.getLong("stock_item_id"));
            }
            soldProduct.setIsReturnRequestOpen(rs.getBoolean("is_return_request_open"));
            soldProduct.setIsProductReturned(rs.getBoolean("is_product_returned"));
            customerOrder.getProductsList().add(soldProduct);
        }
        return customerOrderMap;
    }

    private void setColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        columnsInResultSet = new HashMap<>();
        int columns = resultSetMetaData.getColumnCount();
        for (int col = 1; col <= columns; col++) {
            columnsInResultSet.put(resultSetMetaData.getColumnName(col), true);
        }
    }

}
