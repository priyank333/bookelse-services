/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Priyank Agrawal
 */
public class Hash {

    private MessageDigest messageDigest;
    private final String ALGORITHM = "SHA-512";
    private final static byte[] SALT = { 89, 21, 32, 43, 23 };
    private static final Logger LOGGER = LoggerFactory.getLogger(Hash.class);

    public String getHashValue(String input) {
        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(SALT);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage());
        }
        StringBuilder hashInHexa = new StringBuilder();
        for (byte val : messageDigest.digest(input.getBytes(StandardCharsets.UTF_8))) {
            hashInHexa.append(String.format("%02x", val & 0xff));
        }
        LOGGER.info("Hash value is generated");
        return hashInHexa.toString();
    }
}
