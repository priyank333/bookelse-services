/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.repository;

import com.userservice.config.sql.SqlQueries;
import com.userservice.customer.mapper.AddressMapper;
import com.userservice.customer.model.Address;
import com.userservice.customer.model.Customer;
import java.util.List;
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
public class AddressRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public List<Address> findByCustomer(String customerId) {
        SqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId);
        List<Address> addressList = namedParameterJdbcTemplate.query(sqlQueries.listAddressByCustomer, parameters,
                new AddressMapper());
        if (addressList == null || addressList.isEmpty()) {
            return null;
        } else {
            return addressList;
        }
    }

    public Address findById(Long addressId) {
        SqlParameterSource parameters = new MapSqlParameterSource("addressId", addressId);
        List<Address> addressList = namedParameterJdbcTemplate.query(sqlQueries.listAddressById, parameters,
                new AddressMapper());
        if (addressList == null || addressList.isEmpty()) {
            return null;
        } else {
            return addressList.get(0);
        }
    }

}
