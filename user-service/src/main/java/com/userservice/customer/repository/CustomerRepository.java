/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.repository;

import com.userservice.config.sql.SqlQueries;
import com.userservice.customer.mapper.CustomerMapper;
import com.userservice.customer.model.Customer;
import com.userservice.customer.model.CustomerBankAccount;
import static com.userservice.customer.repository.util.RepositoryUtil.setLike;
import static com.userservice.customer.repository.util.RepositoryUtil.setValue;
import com.userservice.customer.requestpayload.CustomerRequestPayload;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Priyank Agrawal
 */
@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public List<Customer> listCustomers(CustomerRequestPayload customerRequestPayload) {
        String sqlQuery = sqlQueries.listCustomers.concat(generateWhereCondition(customerRequestPayload));

        List<Customer> customerList = jdbcTemplate.query(sqlQuery, new CustomerMapper());
        return customerList;
    }

    public Customer getCustomerById(String customerId) {
        SqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId);
        List<Customer> customerList = namedParameterJdbcTemplate.query(sqlQueries.getCustomerById, parameters,
                new CustomerMapper());
        if (customerList == null || customerList.isEmpty()) {
            return null;
        } else {
            return customerList.get(0);
        }
    }

    private String generateWhereCondition(CustomerRequestPayload customerRequestPayload) {
        StringBuilder whereCondition = new StringBuilder();
        String customerId = customerRequestPayload.customerId;
        String contact = customerRequestPayload.contact;
        String emailId = customerRequestPayload.emailId;
        String firstName = customerRequestPayload.firstName;
        String lastName = customerRequestPayload.lastName;
        String area = customerRequestPayload.area;
        String state = customerRequestPayload.state;
        String city = customerRequestPayload.city;
        String bankName = customerRequestPayload.bankName;
        Boolean isEmailVerified = customerRequestPayload.isEmailVerified;
        String pincode = customerRequestPayload.pincode;
        String bankCode = customerRequestPayload.bankCode;
        String ifscCode = customerRequestPayload.ifscCode;
        if (customerId != null) {
            String trimmedValue = customerId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "c", "customer_id", whereCondition);
            }
        }
        if (contact != null) {
            String trimmedValue = contact.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "c", "contact", whereCondition);
            }
        }
        if (emailId != null) {
            String trimmedValue = emailId.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "c", "email_id", whereCondition);
            }
        }
        if (firstName != null) {
            String trimmedValue = firstName.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(customerRequestPayload.firstName, "c", "first_name", whereCondition);
            }
        }
        if (lastName != null) {
            String trimmedValue = lastName.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "c", "last_name", whereCondition);
            }
        }
        if (area != null) {
            String trimmedValue = area.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "a", "area", whereCondition);

            }
        }
        if (state != null) {
            String trimmedValue = state.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "s", "state", whereCondition);

            }
        }
        if (city != null) {
            String trimmedValue = city.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "ct", "city", whereCondition);
            }
        }
        if (pincode != null) {
            String trimmedValue = pincode.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "a", "pincode", whereCondition);
            }
        }
        if (bankCode != null) {
            String trimmedValue = bankCode.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "cba", "bank_code", whereCondition);
            }
        }
        if (bankName != null) {
            String trimmedValue = bankName.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "cba", "bank_name", whereCondition);
            }
        }
        if (ifscCode != null) {
            String trimmedValue = ifscCode.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "cba", "ifsc_code", whereCondition);
            }
        }
        if (isEmailVerified != null) {
            setValue(isEmailVerified, "c", "is_email_verified", whereCondition);
        }

        return whereCondition.toString();
    }

    public Boolean updateBankDetails(CustomerBankAccount customerBankAccount) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accNumber", customerBankAccount.getAccountNumber());
        parameters.put("bankCode", customerBankAccount.getBankCode());
        parameters.put("bankName", customerBankAccount.getBankName());
        parameters.put("ifscCode", customerBankAccount.getIfscCode());
        parameters.put("customerBankAccId", customerBankAccount.getCustomerBankAccountId());
        return namedParameterJdbcTemplate.update(sqlQueries.updateBankDetails, parameters) != 0;
    }

    public Boolean setBankDetailsToCustomer(String customerId, Long regAccId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("regBankAccId", regAccId);
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.update(sqlQueries.setBankDetailsToCustomer, parameters) != 0;
    }

    public Long getCustomerBankAccId(String customerId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.queryForObject(sqlQueries.getCustomerBankAccId, parameters,
                Long.class);
    }

    public Boolean updateCustomerDetails(Customer customer) {
        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("addressLine1", customer.getAddress().getAddressLine1());
//        parameters.put("addressLine2", customer.getAddress().getAddressLine2());
        parameters.put("firstName", customer.getFirstName());
        parameters.put("lastName", customer.getLastName());
//        parameters.put("area", customer.getAddress().getArea().getAreaId());
//        parameters.put("state", customer.getAddress().getState().getStateId());
//        parameters.put("city", customer.getAddress().getCity().getCityId());
        parameters.put("customerId", customer.getCustomerId());
        return namedParameterJdbcTemplate.update(sqlQueries.updateCustomerDetails, parameters) != 0;
    }
}
