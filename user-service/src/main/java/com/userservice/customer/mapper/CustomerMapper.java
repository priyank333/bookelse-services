/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.mapper;

import com.userservice.customer.model.Address;
import com.userservice.customer.model.Area;
import com.userservice.customer.model.City;
import com.userservice.customer.model.Customer;
import com.userservice.customer.model.CustomerBankAccount;
import com.userservice.customer.model.State;
import com.userservice.util.AES;
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
 * @author Priyank Agrawal
 */
public class CustomerMapper implements ResultSetExtractor<List<Customer>> {

    private Map<String, Boolean> columnsInResultSet;

    @Override
    public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        setColumns(rs);
        List<Customer> customerList = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer(rs.getString("customer_id"));
            customer.setContact(rs.getString("contact"));
            customer.setEmailId(rs.getString("email_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setIsEmailVerified(rs.getBoolean("is_email_verified"));
            customer.setIsActive(rs.getBoolean("is_active"));
            customer.setRegisteredOn(rs.getTimestamp("registered_on").toLocalDateTime());
            if (columnsInResultSet.get("customer_bank_account_id") != null) {
                Long customerBankAccId = rs.getLong("customer_bank_account_id");
                if (customerBankAccId != 0l) {
                    CustomerBankAccount customerBankAccount = new CustomerBankAccount(customerBankAccId);
                    customerBankAccount.setAccountNumber(AES.decrypt(rs.getString("account_number")));
                    customerBankAccount.setBankCode(rs.getString("bank_code"));
                    customerBankAccount.setBankName(rs.getString("bank_name"));
                    customerBankAccount.setIfscCode(rs.getString("ifsc_code"));
                    customer.setCustomerBankAccount(customerBankAccount);
                }
            }
            customerList.add(customer);
        }
        return customerList;
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
