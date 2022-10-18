/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.mapper;

import com.coms.model.CustomerOrder;
import com.coms.model.RentalProduct;
import com.coms.model.SoldProduct;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author z0043uwn
 */
public class RentalProductMapper
        implements ResultSetExtractor<List<RentalProduct>> {

    @Override
    public List<RentalProduct> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        List<RentalProduct> rentalProductList = new ArrayList<>();
        while (rs.next()) {
            RentalProduct rentalProduct = new RentalProduct();
            rentalProduct.setDelayCharge(
                    rs.getDouble("delay_charge"));
            rentalProduct.setRefundAmount(
                    rs.getDouble("refund_amount"));
            rentalProduct.setIsEligibleForReturn(
                    rs.getBoolean("is_eligible_for_return"));
            rentalProduct.setDepreciation(
                    rs.getDouble("depreciation"));
            rentalProduct.setDeposite(rs.getDouble("deposite"));
            rentalProduct.setInitialReturnPeriod(
                    rs.getInt("initial_return_period"));
            rentalProduct.setRentedOn(
                    rs.getDate("rented_on").toLocalDate());
            rentalProduct.setIsPeriodExtended(
                    rs.getBoolean("is_period_extended"));
            rentalProduct.setSoldProduct(new SoldProduct(
                    rs.getLong("sold_product_id")));
            rentalProduct.setCustomerOrder(new CustomerOrder(
                    rs.getString("order_number")));
            rentalProduct.setIsLocked(rs.getBoolean("is_locked"));
            rentalProduct.setLockReason(rs.getString("lock_reason"));
            Date dueDate = rs.getDate("due_date");
            if (dueDate != null) {
                rentalProduct.setDueDate(
                        rs.getDate("due_date").toLocalDate());
            }
            rentalProduct.setRentalCharge(
                    rs.getDouble("rental_charge"));
            rentalProduct.setRentPeriod(
                    rs.getInt("rent_period"));
            
            rentalProduct.setIsLocked(rs.getBoolean("is_locked"));
            rentalProduct.setLockReason(rs.getString("lock_reason"));
            rentalProductList.add(rentalProduct);
        }
        return rentalProductList;
    }

}
