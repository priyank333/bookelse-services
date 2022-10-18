/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.repository;

import com.coms.config.sql.SqlQueries;
import com.coms.dto.MailDTO;
import com.coms.mapper.MailDTOMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author z0043uwn
 */
@Repository
public class MailInfoRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public MailDTO getOrderInfoById(String orderNumber) {
        SqlParameterSource parameterSource = new MapSqlParameterSource(
                "orderNumber", orderNumber);
        Map<String, MailDTO> shippingOrders = namedParameterJdbcTemplate.query(
                sqlQueries.getOrderInfoById,
                parameterSource,
                new MailDTOMapper());
        if (shippingOrders == null) {
            return null;
        } else {
            return shippingOrders.get(orderNumber);
        }

    }
}
