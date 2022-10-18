/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.config.mail.description;

import com.userservice.customer.model.OTPFor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Priyank Agrawal
 */
@Component
@PropertySource("classpath:config/mail/description/mail-description.properties")
public class MailDescription {

    @Value("${passwordResetSubject}")
    public String passwordResetSubject;
    @Value("${emailVerificationSubject}")
    public String emailVerificationSubject;
    @Value("${updateBankDetailsSubject}")
    public String updateBankDetailsSubject;
    @Value("${passwordResetMessage}")
    public String passwordResetMessage;
    @Value("${emailVerificationMessage}")
    public String emailVerificationMessage;
    @Value("${updateBankDetailsMessage}")
    public String updateBankDetailsMessage;
    @Value("${otpDescription}")
    public String otpDescription;

    @Bean(name = "OTPMailDescription")
    public Map<OTPFor, Map<String, String>> getOTPMailDescription() {
        Map<OTPFor, Map<String, String>> otpMailBody = new HashMap<>();

        Map<String, String> passwordResetDescription = new HashMap<>();
        passwordResetDescription.put("subject", passwordResetSubject);
        passwordResetDescription.put("message", passwordResetMessage);
        passwordResetDescription.put("otpDescription", otpDescription);
        otpMailBody.put(OTPFor.PASSWORD_RESET, passwordResetDescription);

        Map<String, String> emailVerificationDescription = new HashMap<>();
        emailVerificationDescription.put("subject", emailVerificationSubject);
        emailVerificationDescription.put("message", emailVerificationMessage);
        emailVerificationDescription.put("otpDescription", otpDescription);
        otpMailBody.put(OTPFor.EMAIL_VERIFICATION, emailVerificationDescription);

        Map<String, String> updateBankDetailsDescription = new HashMap<>();
        updateBankDetailsDescription.put("subject", updateBankDetailsSubject);
        updateBankDetailsDescription.put("message", updateBankDetailsMessage);
        updateBankDetailsDescription.put("otpDescription", otpDescription);
        otpMailBody.put(OTPFor.UPDATE_BANK_DETAILS, updateBankDetailsDescription);
        return otpMailBody;
    }
}
