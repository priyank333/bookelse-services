/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.mgr;

import com.catalog.dao.UniversityDao;
import com.catalog.model.ServiceResponse;
import com.catalog.model.University;
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
public class UniversityControllerMgr {

    @Autowired
    private UniversityDao universityDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityControllerMgr.class);

    public ServiceResponse listAllUniversities() {
        Iterable<University> universities = universityDao.findAll();
        if (null != universities) {
            List<University> universityList = Lists.newArrayList(universities);
            return new ServiceResponse(HttpStatus.OK.value(), universityList);
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse addUniversity(University university) {
        university = universityDao.save(university);
        LOGGER.info("Adding university value: {}", university);
        LinkedHashMap<String, Object> responseParam = new LinkedHashMap<>();
        responseParam.put("universityId", university.getUniversityId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteUniversity(Long universityId) {
        LOGGER.info("Deleting university by id: {}", universityId);
        Optional<University> university = universityDao.findById(universityId);
        if (!university.isPresent()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        universityDao.delete(new University(universityId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateUniversity(University university) {
        LOGGER.info("Updating university with new value: {}", university);
        if (null != universityDao.save(university)) {
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

    public ServiceResponse getUniversityById(Long universityId) {
        LOGGER.info("Getting university by id: {}", universityId);
        Optional<University> university = universityDao.findById(universityId);
        if (!university.isPresent()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        return new ServiceResponse(HttpStatus.OK.value(), university.get());
    }
}
