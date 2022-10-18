/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.mapper;

import com.coms.dto.RentalProductDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author z0043uwn
 */
public class RentalProductDTOMapper
        implements ResultSetExtractor<List<RentalProductDTO>> {

    Map<String, Boolean> resultAttr;

    @Override
    public List<RentalProductDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<RentalProductDTO> rentalProducts = new ArrayList<>();
        setColumns(rs);
        while (rs.next()) {
            RentalProductDTO rentalProduct = new RentalProductDTO();
            if (resultAttr.get("order_number") != null) {
                rentalProduct.setOrderNumber(
                        rs.getString("order_number"));
            }
            if (resultAttr.get("customer_id") != null) {
                rentalProduct.setCustomerId(
                        rs.getString("customer_id"));
            }
            if (resultAttr.get("ordered_date") != null) {
                rentalProduct.setOrderedDate(
                        rs.getTimestamp("ordered_date"));
            }
            if (resultAttr.get("sold_product_id") != null) {
                rentalProduct.setSoldProductId(
                        rs.getLong("sold_product_id"));
            }
            if (resultAttr.get("discount") != null) {
                rentalProduct.setDiscount(
                        rs.getDouble("discount"));
            }
            if (resultAttr.get("product_id") != null) {
                rentalProduct.setProductId(
                        rs.getString("product_id"));
            }
            if (resultAttr.get("product_name") != null) {
                rentalProduct.setProductName(
                        rs.getString("product_name"));
            }
            if (resultAttr.get("remarks") != null) {
                rentalProduct.setRemarks(
                        rs.getString("remarks"));
            }
            if (resultAttr.get("deposite") != null) {
                rentalProduct.setDeposite(
                        rs.getDouble("deposite"));
            }
            if (resultAttr.get("sell_price") != null) {
                rentalProduct.setSellPrice(
                        rs.getDouble("sell_price"));
            }
            if (resultAttr.get("rental_product_id") != null) {
                rentalProduct.setRentalProductId(
                        rs.getString("rental_product_id"));
            }
            if (resultAttr.get("delay_charge") != null) {
                rentalProduct.setDelayCharge(
                        rs.getDouble("delay_charge"));
            }
            if (resultAttr.get("depreciation") != null) {
                rentalProduct.setDepreciation(
                        rs.getDouble("depreciation"));
            }
            if (resultAttr.get("due_date") != null) {
                rentalProduct.setDueDate(
                        rs.getTimestamp("due_date"));
            }
            if (resultAttr.get("initial_return_period") != null) {
                rentalProduct.setInitialReturnPeriod(
                        rs.getInt("initial_return_period"));
            }
            if (resultAttr.get("refund_amount") != null) {
                rentalProduct.setRefundAmount(
                        rs.getDouble("refund_amount"));
            }

            if (resultAttr.get("rental_charge") != null) {
                rentalProduct.setRentalCharge(
                        rs.getDouble("rental_charge"));
            }
            if (resultAttr.get("is_eligible_for_return") != null) {
                rentalProduct.setIsEligibleForReturn(
                        rs.getBoolean("is_eligible_for_return"));
            }
            if (resultAttr.get("is_period_extended") != null) {
                rentalProduct.setIsPeriodExtended(
                        rs.getBoolean("is_period_extended"));
            }
            if (resultAttr.get("rent_period") != null) {
                rentalProduct.setRentPeriod(
                        rs.getInt("rent_period"));
            }
            if (resultAttr.get("is_locked") != null) {
                rentalProduct.setIsLocked(rs.getBoolean("is_locked"));
            }
            if (resultAttr.get("lock_reason") != null) {
                rentalProduct.setLockReason(rs.getString("lock_reason"));
            }
            if (resultAttr.get("extended_rent_period") != null) {
                rentalProduct.setExtendedRentalPeriod(
                        rs.getInt("extended_rent_period"));
            }
            if (resultAttr.get("extended_rental_charge") != null) {
                rentalProduct.setExtendedRentalCharge(
                        rs.getDouble("extended_rental_charge"));
            }
            if (resultAttr.get("rented_on") != null) {
                Date rentedOn = rs.getDate("rented_on");
                if (rentedOn != null) {
                    rentalProduct.setRentedOn(
                            rentedOn.toLocalDate());
                }
            }
            if (resultAttr.get("extended_on") != null) {
                Date extendedOn = rs.getDate("extended_on");
                if (extendedOn != null) {
                    rentalProduct.setExtendedOn(
                            extendedOn.toLocalDate());
                }
            }
            if (resultAttr.get("stock_item_id") != null) {
                rentalProduct.setStockItemId(
                        rs.getLong("stock_item_id"));
            }

            if (resultAttr.get("purchased_from") != null) {
                rentalProduct.setPurchasedFrom(
                        rs.getLong("purchased_from"));
            }

            if (resultAttr.get("purchased_on") != null) {
                Date purchasedOn = rs.getDate("purchased_on");
                if (purchasedOn != null) {
                    rentalProduct.setPurchasedOn(purchasedOn.toLocalDate());
                }
            }
            if (resultAttr.get("purchased_price") != null) {
                rentalProduct.setPurchasedPrice(
                        rs.getDouble("purchased_price"));
            }
            if (resultAttr.get("due_days") != null) {
                rentalProduct.setDueDays(
                        rs.getInt("due_days"));
            }
            rentalProducts.add(rentalProduct);
        }
        return rentalProducts;
    }

    private void setColumns(ResultSet rs) throws SQLException {
        resultAttr = new HashMap<>();

        ResultSetMetaData metaData = rs.getMetaData();
        for (int colCount = 1;
                colCount <= rs.getMetaData().getColumnCount();
                colCount++) {
            resultAttr.put(metaData.getColumnName(colCount), true);
        }
    }
}
