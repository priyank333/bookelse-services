/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.google.common.collect.Lists;
import com.userservice.customer.dao.CityDao;
import com.userservice.customer.model.City;
import com.userservice.customer.model.State;
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
public class CityControllerMgr {

    @Autowired
    private CityDao cityDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CityControllerMgr.class);

    public ServiceResponse addCity(City city) {
        city = cityDao.save(city);
        LOGGER.info("isCityValue is added in table : {}", city.getCityId());
        LinkedHashMap<String, Object> responseValues = new LinkedHashMap<>();
        responseValues.put("cityId", city.getCityId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseValues);
    }

    public ServiceResponse listAllCities() {
        Iterable<City> cities = cityDao.findAll();
        if (null != cities) {
            List<City> cityList = Lists.newArrayList(cities);
            return new ServiceResponse(HttpStatus.OK.value(), cityList);
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    public ServiceResponse getCityById(Long cityId) {
        Optional<City> city = cityDao.findById(cityId);
        if (city.isPresent()) {
            return new ServiceResponse(HttpStatus.OK.value(), city.get());
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    public ServiceResponse deleteCity(Long cityId) {
        Optional<City> city = cityDao.findById(cityId);
        if (!city.isPresent()) {
            LOGGER.info("City value is not found in table : {}", cityId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        cityDao.delete(new City(cityId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    public ServiceResponse getCityByState(final Long stateId) {
        List<Object[]> cityByState = cityDao.findByState(new State(stateId));
        if (cityByState.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            List<City> cities = new ArrayList<>();
            for (Object[] values : cityByState) {
                cities.add(new City((Long) values[0], values[1].toString()));
            }
            return new ServiceResponse(HttpStatus.OK.value(), cities);
        }
    }

    public ServiceResponse updateCity(City city) {
        if (null != cityDao.save(city)) {
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            LOGGER.info("Somwthing went wrong with update city : {}", city);
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

}
