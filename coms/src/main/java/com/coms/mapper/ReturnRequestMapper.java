/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.mapper;

import com.coms.model.ReturnRequest;
import com.coms.model.ReturnStatus;
import com.coms.model.ReturnType;
import com.coms.model.SoldProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author z0043uwn
 */
public class ReturnRequestMapper implements ResultSetExtractor<List<ReturnRequest>> {

    @Override
    public List<ReturnRequest> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ReturnRequest> returnRequestList = new ArrayList<>();
        while(rs.next()){
            ReturnRequest returnRequest = new ReturnRequest();
            returnRequest.setReturnRequestId(rs.getObject("return_request_id",
                    UUID.class));
            returnRequest.setCustomerId(rs.getString("customer_id"));
            returnRequest.setIsAmountPaidToCustomer(rs.getBoolean("is_amount_paid_to_customer"));
            returnRequest.setRequestedOn(rs.getDate("requested_on").toLocalDate());
            returnRequest.setRefundAmount(rs.getDouble("refund_amount"));
            returnRequest.setReturnStatus(ReturnStatus.values()[rs.getInt("return_status")]);
            returnRequest.setReturnType(ReturnType.values()[rs.getInt("return_type")]);
            SoldProduct soldProduct = new SoldProduct();
            returnRequest.setSoldProduct(soldProduct);
            soldProduct.setSoldProductId(rs.getLong("sold_product_id"));
            soldProduct.setIsProductOnRent(rs.getBoolean("is_product_on_rent"));
            soldProduct.setProductId(rs.getString("product_id"));
            soldProduct.setProductName(rs.getString("product_name"));
            soldProduct.setOrderNumber(rs.getString("order_number"));
            returnRequestList.add(returnRequest);
        }
        return returnRequestList;
    }

}
