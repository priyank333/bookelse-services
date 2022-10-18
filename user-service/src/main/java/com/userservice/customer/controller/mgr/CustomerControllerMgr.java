/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.controller.mgr;

import com.userservice.customer.dao.BankAccDao;
import com.userservice.customer.dao.CustomerDao;
import com.userservice.customer.exception.OperationError;
import com.userservice.customer.exception.ResourceNotFoundException;
import com.userservice.customer.model.Customer;
import com.userservice.customer.model.CustomerBankAccount;
import com.userservice.customer.model.OTPFor;
import com.userservice.customer.repository.CustomerRepository;
import com.userservice.customer.requestpayload.BankAccRegPayload;
import com.userservice.customer.requestpayload.CustomerRequestPayload;
import com.userservice.model.ServiceResponse;
import com.userservice.util.AES;
import com.userservice.util.Hash;
import com.userservice.util.UserServiceUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
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
public class CustomerControllerMgr {

        @Autowired
        private CustomerDao customerDao;

        @Autowired
        private OTPControllerMgr oTPServiceMgr;

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private VerificationControllerMgr verificationMgr;

        @Autowired
        private BankAccDao bankAccDao;

        private static final Logger LOGGER = LoggerFactory.getLogger(CustomerControllerMgr.class);

        public ServiceResponse listCustomers(CustomerRequestPayload customerRequestPayload) {
                LOGGER.info("Received request to list customer details by this payload {}", customerRequestPayload);
                List<Customer> customerList = customerRepository.listCustomers(customerRequestPayload);
                if (customerList.isEmpty()) {
                        return new ServiceResponse(HttpStatus.NO_CONTENT.value());
                }
                return new ServiceResponse(HttpStatus.OK.value(), customerList);
        }

        public ServiceResponse getCustomerById(String customerId) {
                LOGGER.info("Received request to list customer details by id {}", customerId);
                Customer customer = customerRepository.getCustomerById(customerId);
                if (customer == null) {
                        return new ServiceResponse(HttpStatus.NO_CONTENT.value());
                } else {
                        return new ServiceResponse(HttpStatus.OK.value(), customer);
                }
        }

        @Transactional(rollbackOn = Exception.class)
        public ServiceResponse registerCustomer(Customer customer) {

                customer.setIsEmailVerified(Boolean.FALSE);
                customer.setIsActive(true);
                if (customerDao.existsCustomerByContact(customer.getContact())) {
                        Map<String, Object> resource = new HashMap<String, Object>();
                        resource.put("contact", customer.getContact());
                        throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                                        "Contact number is already registered with us.", resource,
                                        "CustomerRegistration");
                }
                if (customerDao.existsCustomerByEmailId(customer.getEmailId())) {
                        Map<String, Object> resource = new HashMap<String, Object>();
                        resource.put("emailAddress", customer.getEmailId());
                        throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                                        "Email id is already registered with us.", resource, "CustomerRegistration");
                }

                if (customer.getCustomerBankAccount() != null) {
                        encryptCustomerAccountNumber(customer.getCustomerBankAccount());
                }
                customer.setCustomerId(new UserServiceUtil().generateCustomerId());
                customer.setRegisteredOn(LocalDateTime.now());
                Customer newAddedCustomer = customerDao.save(customer);
                if (newAddedCustomer == null) {
                        LOGGER.info("Something went wrong while registration of customer : {}", customer);
                } else {
                        LOGGER.info("Customer is registered with system : {}", customer);
                }
                ServiceResponse serviceResponse = oTPServiceMgr.sendOTP(customer.getEmailId(),
                                OTPFor.EMAIL_VERIFICATION);
                if (serviceResponse.getStatusCode() == HttpStatus.OK.value()) {
                        LOGGER.info("While registration OTP is sent to the customer " + "whose id : {}",
                                        customer.getCustomerId());
                } else {
                        LOGGER.info("Something went wrong while sending OTP to customer : ", customer.getCustomerId());
                }
                LinkedHashMap<String, Object> responseValues = new LinkedHashMap<>();
                responseValues.put("customerId", customer.getCustomerId());
                return new ServiceResponse(HttpStatus.CREATED.value(), responseValues);
        }

        @Transactional(rollbackOn = Exception.class)
        private void encryptCustomerAccountNumber(CustomerBankAccount bankAccount) {
                LOGGER.info("Encrypting customer bank account details input : {}", bankAccount);
                bankAccount.setAccountNumber(AES.encrypt(bankAccount.getAccountNumber()));

        }

        @Transactional(rollbackOn = Exception.class)
        public ServiceResponse updateCustomerBankAccountDetails(CustomerBankAccount customerBankAccount,
                        String customerId) {
                LOGGER.info("Updating customer bank account details whose id : {}", customerId);
                ServiceResponse serviceResponse = new ServiceResponse();
                Long customerBankAccId = customerDao.getCustomerBankAccIdByCustomer(customerId);

                if (customerBankAccId == null) {
                        throw new ResourceNotFoundException("customer", customerId,
                                        "Customer is not found by requested id");
                }
                customerBankAccount.setAccountNumber(AES.encrypt(customerBankAccount.getAccountNumber()));
                customerBankAccount.setCustomerBankAccountId(customerBankAccId);
                Boolean isUpdated = customerRepository.updateBankDetails(customerBankAccount);
                HashMap<String, Boolean> responseParam = new LinkedHashMap<>();
                responseParam.put("isUpdated", isUpdated);
                serviceResponse.setResponse(responseParam);
                serviceResponse.setStatusCode(HttpStatus.OK.value());
                return serviceResponse;
        }

        @Transactional(rollbackOn = Exception.class)
        public ServiceResponse verifyCustomerMailAddress(String verificationCode, String customerId) {
                if (verificationMgr.otpVerification(customerId, verificationCode, OTPFor.EMAIL_VERIFICATION)) {
                        LOGGER.info("Customer email verified successfully of customer : {}", customerId);
                        customerDao.changeEmailVerificationStatus(Boolean.TRUE, customerId);
                        return new ServiceResponse(HttpStatus.OK.value(), "Email is verified successfully");
                } else {
                        LOGGER.info("Email verification faild of customer : {}", customerId);
                        return new ServiceResponse(HttpStatus.PRECONDITION_FAILED.value(), "Invalid OTP");
                }
        }

        @Transactional(rollbackOn = Exception.class)
        public ServiceResponse resetPassword(String verificationCode, String emailId, String password) {
                String customerId = customerDao.findCustomerIdByEmail(emailId);
                if (verificationMgr.otpVerification(customerId, verificationCode, OTPFor.PASSWORD_RESET)) {
                        if (customerDao.updatePassword(new Hash().getHashValue(password), customerId) > 0) {
                                LOGGER.info("Password has successfully reset of customer : {}", customerId);
                                return new ServiceResponse(HttpStatus.OK.value(), "Password is reset successfully");
                        } else {
                                return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                "Something went wrong.");
                        }
                } else {
                        return new ServiceResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid OTP");
                }

        }

        public ServiceResponse getEmailAddressByCustomer(String customerId) {
                String emailAddress = customerDao.findEmailIdByCustomerId(customerId);
                if (emailAddress != null) {
                        LOGGER.info("Email address found : {} of customer : {}", emailAddress, customerId);
                        Map<String, String> response = new HashMap<>();
                        response.put("emailAddress", emailAddress);
                        return new ServiceResponse(HttpStatus.OK.value(), response);
                } else {
                        LOGGER.info("Email address is not exist of customer : {}", customerId);
                        return new ServiceResponse(HttpStatus.NO_CONTENT.value());
                }
        }

        public ServiceResponse isAccountActive(String customerId) {
                LOGGER.info("Checking isAccountActive for customerId: {}", customerId);
                Boolean isAccActive = customerDao.isAccountActiveById(customerId);
                if (isAccActive == null || !isAccActive) {
                        LOGGER.info("isAccountActive : {} for customerId: {}", false, customerId);
                        return new ServiceResponse(HttpStatus.OK.value(), false);
                } else {
                        LOGGER.info("isAccountActive : {} for customerId: {}", true, customerId);
                        return new ServiceResponse(HttpStatus.OK.value(), true);
                }
        }

        @Transactional(rollbackOn = Exception.class)
        public ServiceResponse addBankDetails(BankAccRegPayload accRegPayload) {
                Boolean isAccActive = customerDao.isAccountActiveById(accRegPayload.customerId);
                if (isAccActive == null || !isAccActive) {
                        throw new ResourceNotFoundException("customer", accRegPayload.customerId,
                                        "Customer has not registered with us or not active.");
                }
                Long customerBankAccId = customerRepository.getCustomerBankAccId(accRegPayload.customerId);
                if (customerBankAccId != null) {
                        throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                                        "Customer has registered bank details.", "AddBankDetails");
                }

                encryptCustomerAccountNumber(accRegPayload.customerBankAccount);
                Long bankAccRegId = bankAccDao.save(accRegPayload.customerBankAccount).getCustomerBankAccountId();
                Boolean operationStatus = customerRepository.setBankDetailsToCustomer(accRegPayload.customerId,
                                bankAccRegId);
                if (operationStatus) {
                        Map<String, Long> resp = new HashMap<>();
                        resp.put("bankAccRegId", bankAccRegId);
                        return new ServiceResponse(HttpStatus.CREATED.value(), resp);
                } else {
                        return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), operationStatus);
                }
        }

        public ServiceResponse hasCustomerRegisteredBank(String customerId) {
                Boolean isAccActive = customerDao.isAccountActiveById(customerId);
                if (isAccActive == null || !isAccActive) {
                        throw new ResourceNotFoundException("customer", customerId,
                                        "Customer has not registered with us or not active.");
                }
                Long customerBankAccId = customerRepository.getCustomerBankAccId(customerId);
                Map<String, Boolean> resp = new HashMap<>();

                if (customerBankAccId == null) {
                        resp.put("hasBankAccount", false);
                        return new ServiceResponse(HttpStatus.OK.value(), resp);
                } else {
                        resp.put("hasBankAccount", true);
                        return new ServiceResponse(HttpStatus.OK.value(), resp);
                }
        }

        public ServiceResponse updateUserDetails(Customer customer) {
                Boolean isUpdated = customerRepository.updateCustomerDetails(customer);
                ServiceResponse serviceResponse = new ServiceResponse();
                serviceResponse.setResponse(isUpdated);
                serviceResponse.setStatusCode(HttpStatus.OK.value());
                return serviceResponse;
        }
}
