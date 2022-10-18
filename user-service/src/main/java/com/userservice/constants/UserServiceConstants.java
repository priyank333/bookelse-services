/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.constants;

/**
 *
 * @author Priyank Agrawal
 */
public final class UserServiceConstants {

    public final static String USER_ADMIN = "ADMIN";
    public final static String USER_CUSTOMER = "CUSTOMER";
    public final static String ALLOWED_HOST = "*";
    public final static String PASSWORD_RESET_SUBJECT = "One Time Password (OTP) Confirmation | Password reset | BookElse.com";
    public final static String EMAIL_VERIFICATION_SUBJECT = "One Time Password (OTP) Confirmation | Email Verification | BookElse.com";
    public final static String PASSWORD_RESET_MESSAGE = "You recently requested to reset your password for your account. Please use this OTP for reset password.";
    public final static String EMAIL_VERIFICATION_MESSAGE = "As you registered with us it is required to verify your email address for further communication. Please use this OTP for verification.";
    public final static String OTP_DESCRIPTION = "This OTP is valid only for 2 hours.";
    // Value must be in hours
    public final static Integer TIME_INTERVAL_FOR_DELETE_OTP = 2;
}
