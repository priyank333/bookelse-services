/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.mapper;

import com.coms.dto.MailDTO;
import com.coms.dto.ProductDTO;
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
public class MailDTOMapper implements ResultSetExtractor<Map<String, MailDTO>> {

    private Map<String, Boolean> columnsInResultSet;

    @Override
    public Map<String, MailDTO> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        setColumns(rs);
        Map<String, MailDTO> orders = new HashMap<>();
        while (rs.next()) {
            String orderNumber = rs.getString("order_number");
            if (orders.get(orderNumber) == null) {
                MailDTO mailDTO = new MailDTO();
                mailDTO.setOrderNumber(orderNumber);
                mailDTO.setPayableAmount(0.0);
                orders.put(rs.getString("order_number"), mailDTO);
                if (columnsInResultSet.get("received_on") != null) {
                    Date receivedOn = rs.getDate("received_on");
                    if (receivedOn != null) {
                        mailDTO.setOrderDate(receivedOn.toLocalDate());
                    }
                }
                if (columnsInResultSet.get("shipped_on") != null) {
                    Date shippedOn = rs.getDate("shipped_on");
                    if (shippedOn != null) {
                        mailDTO.setShippedOn(shippedOn.toLocalDate());
                    }
                }
                if (columnsInResultSet.get("delivered_on") != null) {
                    Date deliveredOn = rs.getDate("delivered_on");
                    if (deliveredOn != null) {
                        mailDTO.setDeliveredOn(deliveredOn.toLocalDate());
                    }
                }
                if (columnsInResultSet.get("shipping_charge") != null) {
                    mailDTO.setShippingCharge(rs.getDouble("shipping_charge"));
                    mailDTO.setPayableAmount(mailDTO.getPayableAmount()
                            + mailDTO.getShippingCharge());
                }
                if (columnsInResultSet.get("customer_id") != null) {
                    mailDTO.setCustomerId(rs.getString("customer_id"));
                }
                if (columnsInResultSet.get("payment_mode") != null) {
                    String mode = rs.getString("payment_mode");
                    if (mode != null) {
                        mailDTO.setPaymentMode(mode.toUpperCase());
                    }
                }
                mailDTO.setProductList(new ArrayList<>());
            }

            ProductDTO productDTO = new ProductDTO();
            if (columnsInResultSet.get("quantity") != null) {
                productDTO.setQuantity(rs.getInt("quantity"));
            }
            if (columnsInResultSet.get("product_name") != null) {
                productDTO.setProductName(rs.getString("product_name"));
            }
            if (columnsInResultSet.get("sell_price") != null) {
                productDTO.setSellPrice(rs.getDouble("sell_price"));
            }
            if (columnsInResultSet.get("discount") != null) {
                productDTO.setDiscount(rs.getDouble("discount"));
            }
            if (columnsInResultSet.get("net_price") != null) {
                MailDTO mailDTO = orders.get(orderNumber);
                Double netPrice = rs.getDouble("net_price");
                mailDTO.setPayableAmount(mailDTO.getPayableAmount() + (netPrice
                        * productDTO.getQuantity()));
                productDTO.setFinalPrice(netPrice);
            }
            orders.get(orderNumber).getProductList().add(productDTO);
        }
        return orders;
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
