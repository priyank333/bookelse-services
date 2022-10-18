/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.google.common.collect.Lists;
import com.userservice.customer.dao.AreaDao;
import com.userservice.customer.model.Area;
import com.userservice.customer.model.City;
import com.userservice.model.ServiceResponse;
import java.util.ArrayList;
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
public class AreaControllerMgr {

    @Autowired
    private AreaDao areaDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(AreaControllerMgr.class);

    public ServiceResponse addArea(Area area) {
        area = areaDao.save(area);
        LOGGER.info("Is Area added in table : {}", area.getAreaId() != null);
        LOGGER.info("Area Id : {}", area.getAreaId());
        LinkedHashMap<String, Object> responseValues = new LinkedHashMap<>();
        responseValues.put("areaId", area.getAreaId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseValues);
    }

    public ServiceResponse listAllAreas() {
        LOGGER.info("Listing all areas");
        Iterable<Area> areas = areaDao.findAll();
        if (null != areas) {
            return new ServiceResponse(HttpStatus.OK.value(), Lists.newArrayList(areas));
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    public ServiceResponse getAreasByCity(final Long cityId) {
        LOGGER.info("Retrieving area by city with value of {}", cityId);
        List<Object[]> areaByCity = areaDao.findByCity(new City(cityId));
        if (areaByCity.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            List<Area> areas = new ArrayList<>();
            areaByCity.forEach((values) -> {
                areas.add(new Area((Long) values[0], values[1].toString(), values[2].toString()));
            });
            return new ServiceResponse(HttpStatus.OK.value(), areas);
        }
    }

    public ServiceResponse getAreaById(Long areaId) {
        LOGGER.info("Retrieving area by id {}", areaId);
        Optional<Area> area = areaDao.findById(areaId);
        if (area.isPresent()) {
            return new ServiceResponse(HttpStatus.OK.value(), area.get());
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    public ServiceResponse deleteArea(Long areaId) {
        LOGGER.info("Deleting area by id {}", areaId);
        Optional<Area> area = areaDao.findById(areaId);
        if (!area.isPresent()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        areaDao.delete(new Area(areaId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    public ServiceResponse updateArea(Area area) {
        LOGGER.info("Updating area {}", area);
        area = areaDao.save(area);
        if (null != area) {
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

    public ServiceResponse isPincodeExist(String pincode) {
        LOGGER.info("Checking pincode is exist by pincode : {}", pincode);
        if (areaDao.existsByPincode(pincode)) {
            return new ServiceResponse((HttpStatus.OK.value()), true);
        } else {
            return new ServiceResponse((HttpStatus.OK.value()), false);
        }
    }
}
