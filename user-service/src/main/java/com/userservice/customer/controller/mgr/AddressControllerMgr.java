/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.userservice.customer.dao.AddressDao;
import com.userservice.customer.model.Address;
import com.userservice.customer.repository.AddressRepository;
import com.userservice.model.ServiceResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class AddressControllerMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressControllerMgr.class);

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private AddressRepository addressRepository;

    public ServiceResponse addAddress(Address address) {
        address = addressDao.save(address);
        LOGGER.info("IsAddress is added in table : {}", address.getAddressId());
        LinkedHashMap<String, Object> responseValues = new LinkedHashMap<>();
        responseValues.put("addressId", address.getAddressId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseValues);
    }

    public ServiceResponse deleteAddress(Long addressId) {
        Optional<Address> address = addressDao.findById(addressId);
        if (!address.isPresent()) {
            LOGGER.info("Address value is not found in table : {}", addressId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        addressDao.delete(new Address(addressId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    public ServiceResponse updateAddress(Address address) {
        if (null != addressDao.save(address)) {
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            LOGGER.info("Somwthing went wrong with update city : {}", address);
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

    public ServiceResponse findById(Long addressId) {
        Address address = addressRepository.findById(addressId);
        if (address == null) {
            LOGGER.info("Address value is not found in table : {}", addressId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        return new ServiceResponse(HttpStatus.OK.value(), address);
    }

    public ServiceResponse findByCustomer(String customerId) {
        List<Address> addressList = addressRepository.findByCustomer(customerId);
        if (addressList == null) {
            LOGGER.info("Address value is not found in table : {}", customerId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        return new ServiceResponse(HttpStatus.OK.value(), addressList);
    }
}
