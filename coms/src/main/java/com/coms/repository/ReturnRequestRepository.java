/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.repository;

import com.coms.config.sql.SqlQueries;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author z0043uwn
 */
@Repository
public class ReturnRequestRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public Long getSoldProductByReturnRequest(String returnRequestId) {
        Map<String, Object> param = new HashMap();
        param.put("returnRequestId", returnRequestId);
        return namedParameterJdbcTemplate.queryForObject(
                sqlQueries.getSoldProductByReturnRequest,
                param,
                Long.class);

    }
}
