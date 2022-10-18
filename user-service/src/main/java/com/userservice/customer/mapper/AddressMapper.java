/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.mapper;

import com.userservice.customer.model.Address;
import com.userservice.customer.model.AddressType;
import com.userservice.customer.model.Area;
import com.userservice.customer.model.City;
import com.userservice.customer.model.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Priyank Agrawal
 */
public class AddressMapper implements ResultSetExtractor<List<Address>> {
    
    @Override
    public List<Address> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        List<Address> addressList = new ArrayList<>();
        while (rs.next()) {
            Address address = new Address(rs.getLong("address_id"));
            String addressLine1 = rs.getString("address_line1");
            if (addressLine1 != null) {
                address.setAddressLine1(addressLine1);
            }
            String addressLine2 = rs.getString("address_line2");
            if (addressLine2 != null) {
                address.setAddressLine2(addressLine2);
            }
            address.setAddressType(AddressType.values()[rs.getInt("address_type")]);
            address.setArea(new Area(rs.getLong("area_id"),
                    rs.getString("area"),
                    rs.getString("pincode")));
            address.setCity(new City(rs.getLong("city_id"),
                    rs.getString("city")));
            address.setState(new State(rs.getLong("state_id"),
                    rs.getString("state")));
            addressList.add(address);
        }
        return addressList;
    }
}
