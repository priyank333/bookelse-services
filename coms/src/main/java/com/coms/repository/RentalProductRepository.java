/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.repository;

import com.coms.config.sql.SqlQueries;
import com.coms.dto.RentalProductDTO;
import com.coms.mapper.RentalProductDTOMapper;
import com.coms.mapper.RentalProductMapper;
import com.coms.model.RentalProduct;
import com.coms.payload.RentalProductForAdminPayload;
import com.coms.payload.RentalProductPayload;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.coms.repository.util.RepositoryUtil.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 *
 * @author z0043uwn
 */
@Repository
public class RentalProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public List<RentalProductDTO> listRentalProducts(
            RentalProductPayload rentalProductPayload) {
        String sqlQuery = sqlQueries.listAllRentalProducts.
                concat(generateWhereCondition(rentalProductPayload));
        List<RentalProductDTO> listRentalProducts
                = jdbcTemplate.query(sqlQuery, new RentalProductDTOMapper());
        if (listRentalProducts != null) {
            return listRentalProducts;
        }
        return Collections.emptyList();
    }

    public List<RentalProductDTO> listRentalProducts(
            RentalProductForAdminPayload rentalProductPayload) {
        String sqlQuery = sqlQueries.listAllRentalProductsForAdmin.
                concat(generateWhereCondition(rentalProductPayload)).
                concat(generateWhereConditionForAdmin(rentalProductPayload));
        List<RentalProductDTO> listRentalProducts
                = jdbcTemplate.query(sqlQuery, new RentalProductDTOMapper());
        if (listRentalProducts != null) {
            return listRentalProducts;
        }
        return Collections.emptyList();
    }

    private String generateWhereCondition(
            RentalProductPayload rentalProductPayload) {
        StringBuilder whereCondition = new StringBuilder();
        String customerId = rentalProductPayload.customerId;
        String orderNumber = rentalProductPayload.orderNumber;
        String productName = rentalProductPayload.productName;
        LocalDate fromDate = rentalProductPayload.fromDate;
        LocalDate toDate = rentalProductPayload.toDate;
        if (customerId != null) {
            String trimmedValue = customerId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue,
                        "co", "customer_id", whereCondition);
            }
        }
        if (orderNumber != null) {
            String trimmedValue = orderNumber.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue,
                        "co", "order_number", whereCondition);
            }
        }
        if (fromDate != null && toDate != null) {
            setDateRange(fromDate,
                    toDate, "co", "ordered_date",
                    whereCondition);
        }
        if (productName != null) {
            String trimmedValue = productName.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue,
                        "sp",
                        "product_name",
                        whereCondition);
            }
        }

        return whereCondition.toString();
    }

    private String generateWhereConditionForAdmin(
            RentalProductForAdminPayload rentalProductPayload) {
        StringBuilder whereCondition = new StringBuilder();
        Long purchasedFrom = rentalProductPayload.purchasedFrom;
        LocalDate fromDatePurchasedOn = rentalProductPayload.fromDatePurchasedOn;
        LocalDate toDatePurchasedOn = rentalProductPayload.toDatePurchasedOn;
        if (purchasedFrom != null) {
            setValue(purchasedFrom,
                    "sp", "purchased_from", whereCondition);
        }
        if (fromDatePurchasedOn != null && toDatePurchasedOn != null) {
            setDateRange(fromDatePurchasedOn,
                    toDatePurchasedOn,
                    "sp", "purchased_on", whereCondition);
        }
        return whereCondition.toString();
    }

    public RentalProduct getRentalProductBySoldProduct(Long soldProductId) {

        SqlParameterSource parameters = new MapSqlParameterSource(
                "soldProductId", soldProductId);
        return namedParameterJdbcTemplate.query(
                sqlQueries.getRentalProductDetails, parameters,
                (ResultSet rs, int i) -> {
                    RentalProduct rentalProduct = new RentalProduct();
                    rentalProduct.setDelayCharge(rs.getDouble("delay_charge"));
                    rentalProduct.setRefundAmount(rs.getDouble("refund_amount"));
                    rentalProduct.setDeposite(rs.getDouble("deposite"));
                    rentalProduct.setIsEligibleForReturn(
                            rs.getBoolean("is_eligible_for_return"));
                    return rentalProduct;
                }).get(0);
    }

    public RentalProduct getRentalProductForExtendPeriod(String rentalProductId) {
        SqlParameterSource parameters = new MapSqlParameterSource(
                "rentalProductId", rentalProductId);
        List<RentalProduct> rentalProducts = namedParameterJdbcTemplate.query(
                sqlQueries.getRentalProductById,
                parameters, new RentalProductMapper());
        if (rentalProducts == null) {
            return null;
        } else {
            return rentalProducts.get(0);
        }
    }

    public Boolean makeEntryInRentalProduct(RentalProduct rentalProduct) {
        Boolean isInserted = jdbcTemplate.update(
                sqlQueries.makeEntryInRentalProduct,
                rentalProduct.getRentalProductId(),
                rentalProduct.getDelayCharge(),
                rentalProduct.getDepreciation(),
                rentalProduct.getDueDate(),
                rentalProduct.getInitialReturnPeriod(),
                rentalProduct.getIsEligibleForReturn(),
                rentalProduct.getIsPeriodExtended(),
                rentalProduct.getRefundAmount(),
                rentalProduct.getDeposite(),
                rentalProduct.getRentPeriod(),
                rentalProduct.getRentalCharge(),
                rentalProduct.getRentedOn(),
                rentalProduct.getCustomerOrder().getOrderNumber(),
                rentalProduct.getSoldProduct().getSoldProductId(),
                rentalProduct.getIsLocked(),
                rentalProduct.getLockReason(),
                rentalProduct.getExtendedRentPeriod(),
                rentalProduct.getExtendedRentalCharge()) > 0;
        return isInserted;
    }

    public List<RentalProductDTO> listDelayedRentalItems() {
        String sqlQuery = sqlQueries.listDelayedRentalItems;
        List<RentalProductDTO> listRentalProducts
                = jdbcTemplate.query(sqlQuery, new RentalProductDTOMapper());
        if (listRentalProducts != null) {
            return listRentalProducts;
        }
        return Collections.emptyList();
    }

    public void updateDelayedCharges(List<RentalProduct> rentalProductList) {
        String sqlQuery = sqlQueries.updateRentalProductDelayedCharges;
        jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RentalProduct rentalProduct = rentalProductList.get(i);
                ps.setDouble(1, rentalProduct.getDelayCharge());
                ps.setBoolean(2, rentalProduct.getIsEligibleForReturn());
                ps.setDouble(3, rentalProduct.getRefundAmount());
                ps.setString(4, rentalProduct.getRentalProductId());
            }

            @Override
            public int getBatchSize() {
                return rentalProductList.size();
            }
        });
    }
}
