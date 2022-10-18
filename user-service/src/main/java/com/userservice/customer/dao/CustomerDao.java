/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.dao;

import com.userservice.customer.model.Customer;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Priyank Agrawal
 */
public interface CustomerDao extends JpaRepository<Customer, Long> {

        @Query("SELECT c.customerId from Customer c " + "WHERE c.emailId = :emailId AND c.password = :password")
        public String getCustomerIdByEmailIdAndPassword(@Param("emailId") String emailId,
                        @Param("password") String password);

        public Boolean existsCustomerByContact(String contact);

        public Boolean existsCustomerByEmailId(String emailId);

        @Transactional
        @Modifying
        @Query("UPDATE Customer c set c.isEmailVerified = :isEmailVerified where " + "c.customerId = :customerId")
        public Integer changeEmailVerificationStatus(@Param("isEmailVerified") Boolean isEmailVerified,
                        @Param("customerId") String customerId);

        @Query("SELECT c.firstName from Customer c where c.emailId = :emailId")
        public String findFirstNameByEmailId(@Param("emailId") String emailId);

        @Query("SELECT c.customerId FROM Customer c where c.emailId = :emailId")
        public String findCustomerIdByEmail(@Param("emailId") String emailId);

        @Transactional
        @Modifying
        @Query(value = "UPDATE customer set password = :password "
                        + "where customer_id = :customerId", nativeQuery = true)
        public Integer updatePassword(@Param("password") String password, @Param("customerId") String customerId);

        @Query("SELECT c.emailId FROM Customer c where c.customerId = :customerId")
        public String findEmailIdByCustomerId(@Param("customerId") String customerId);

        @Query("SELECT c.isActive FROM Customer c where c.customerId = :customerId")
        public Boolean isAccountActiveById(@Param("customerId") String customerId);

        @Query("SELECT c.isActive FROM Customer c where c.emailId = :emailId")
        public Boolean isAccountActiveByEmail(@Param("emailId") String emailAddress);

        @Query("SELECT c.customerBankAccount.customerBankAccountId FROM Customer c "
                        + "where c.customerId = :customerId")
        public Long getCustomerBankAccIdByCustomer(@Param("customerId") String customerId);
}
