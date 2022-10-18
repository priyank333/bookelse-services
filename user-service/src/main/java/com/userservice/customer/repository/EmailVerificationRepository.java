/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.repository;

import com.userservice.config.sql.SqlQueries;
import com.userservice.customer.model.OTPFor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Priyank Agrawal
 */
@Repository
public class EmailVerificationRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public String getVerificationCode(String customerId, OTPFor oTPFor) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        parameters.put("otpFor", oTPFor.toString());
        return namedParameterJdbcTemplate.queryForObject(sqlQueries.getVerificationCode, parameters, String.class);
    }

    public Boolean deleteOTPByCustomerAndType(String customerId, OTPFor oTPFor) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        parameters.put("otpFor", oTPFor.toString());
        return namedParameterJdbcTemplate.update(sqlQueries.deleteOTP, parameters) != 0;
    }

    public Boolean deleteUnusedOTPs(Integer lastNMinutes) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hourTime", lastNMinutes);
        return namedParameterJdbcTemplate.update(sqlQueries.deleteUnusedOTPs, parameters) != 0;
    }
}
