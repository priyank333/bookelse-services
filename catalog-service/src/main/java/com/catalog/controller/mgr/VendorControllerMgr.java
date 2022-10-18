/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.mgr;

import com.catalog.dao.VendorDao;
import com.catalog.model.ServiceResponse;
import com.catalog.model.Vendor;
import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class VendorControllerMgr {

    @Autowired
    private VendorDao vendorDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(VendorControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse addVendor(Vendor vendor) {
        LOGGER.info("Adding vendor with new value: {}", vendor);
        vendor = vendorDao.save(vendor);
        LinkedHashMap<String, Object> responseParam = new LinkedHashMap<>();
        responseParam.put("vendorId", vendor.getVendorId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseParam);
    }

    public ServiceResponse listAllVendors() {
        Iterable<Vendor> vendors = vendorDao.findAll();
        if (null != vendors) {
            List<Vendor> vendorList = Lists.newArrayList(vendors);
            return new ServiceResponse(HttpStatus.OK.value(), vendorList);
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteVendor(Long vendorId) {
        Optional<Vendor> vendor = vendorDao.findById(vendorId);
        if (!vendor.isPresent()) {
            LOGGER.info("Deleting vendor by id: {}", vendorId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        vendorDao.delete(new Vendor(vendorId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateVendor(Vendor vendor) {
        LOGGER.info("Updating vendor new value: {}", vendor);
        if (null != vendorDao.save(vendor)) {
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

    public ServiceResponse getVendorById(Long vendorId) {
        Optional<Vendor> vendor = vendorDao.findById(vendorId);
        if (!vendor.isPresent()) {
            LOGGER.info("Vendor not found by id: {}", vendorId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        return new ServiceResponse(HttpStatus.OK.value(), vendor.get());
    }

}
