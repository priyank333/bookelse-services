/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.config.sql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Priyank Agrawal
 */
@Component
@PropertySource("classpath:config/sql/queries.properties")
public class SqlQueries {

    @Value("${listCustomers}")
    public String listCustomers;

    @Value("${getCustomerById}")
    public String getCustomerById;

    @Value("${getVerificationCode}")
    public String getVerificationCode;

    @Value("${deleteOTP}")
    public String deleteOTP;

    @Value("${deleteUnusedOTPs}")
    public String deleteUnusedOTPs;

    @Value("${updateBankDetails}")
    public String updateBankDetails;

    @Value("${setBankDetailsToCustomer}")
    public String setBankDetailsToCustomer;

    @Value("${getCustomerBankAccId}")
    public String getCustomerBankAccId;

    @Value("${updateCustomerDetails}")
    public String updateCustomerDetails;

    @Value("${listAddressByCustomer}")
    public String listAddressByCustomer;

    @Value("${listAddressById}")
    public String listAddressById;
}
