/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.google.common.collect.Lists;
import com.userservice.customer.dao.StateDao;
import com.userservice.customer.model.State;
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
public class StateControllerMgr {

    @Autowired
    private StateDao stateDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(StateControllerMgr.class);

    public ServiceResponse addState(State state) {
        state = stateDao.save(state);
        LinkedHashMap<String, Long> responseParam = new LinkedHashMap<>();
        ServiceResponse serviceResponse = new ServiceResponse();
        if (state.getStateId() != null) {
            LOGGER.info("State new value is inserted in DB with stateId : {}", state.getStateId());
            responseParam.put("stateId", state.getStateId());
            serviceResponse.setResponse(responseParam);
            serviceResponse.setStatusCode(HttpStatus.CREATED.value());
        } else {
            LOGGER.info("Something wrong with insertion operation in state table", state.getState());
            serviceResponse.setResponse("Insertion is failed");
            serviceResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return serviceResponse;
    }

    public ServiceResponse listAllStates() {
        Iterable<State> states = stateDao.findAll();
        if (null != states) {
            List<State> stateList = Lists.newArrayList(states);
            return new ServiceResponse(HttpStatus.OK.value(), stateList);
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    public ServiceResponse deleteState(Long stateId) {
        Optional<State> area = stateDao.findById(stateId);
        if (!area.isPresent()) {
            LOGGER.info("While deleting state is not found by stateId : {}", stateId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
        stateDao.delete(new State(stateId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    public ServiceResponse getStateById(Long stateId) {
        LOGGER.info("Getting state details by stateId : {}", stateId);
        Optional<State> state = stateDao.findById(stateId);
        if (state.isPresent()) {
            return new ServiceResponse(HttpStatus.OK.value(), state.get());
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    public ServiceResponse updateState(State state) {
        if (null != stateDao.save(state)) {
            LOGGER.info("State details is updated : {}", state);
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            LOGGER.info("State details is not updated : {}", state);
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

}
