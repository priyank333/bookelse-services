/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Priyank Agrawal
 */
public class UserServiceUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceUtil.class);
    private final int CUSTOMERID_LEN = 8;
    private final int OTP_LENGTH_OTP = 6;
    private final String KEY_STRING_OTP = "089756423123409875460912678086554321";
    private final String ALGORITHM_TOTP = "HmacSHA1";
    private static byte[] OTPKey;

    public UserServiceUtil() {
        try {
            OTPKey = Hex.decodeHex(KEY_STRING_OTP.toCharArray());
        } catch (org.apache.commons.codec.DecoderException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public String generateOTP() {
        Long otp = 0l;
        try {
            otp = TOTP.generateTOTP(OTPKey, System.currentTimeMillis(), OTP_LENGTH_OTP, ALGORITHM_TOTP);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return otp.toString();
    }

    public String generateCustomerId() {
        return generateRandomNumericValue(CUSTOMERID_LEN);
    }

    private String generateRandomNumericValue(Integer length) {
        String randomNumber = "";
        do {
            randomNumber = RandomStringUtils.randomNumeric(length);
            randomNumber = randomNumber.replaceFirst("^0+(?!$)", "");
        } while (randomNumber.length() < length);
        return randomNumber;
    }
}
