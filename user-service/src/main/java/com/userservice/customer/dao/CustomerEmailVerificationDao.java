/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.dao;

import com.userservice.customer.model.CustomerEmailVerification;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Priyank Agrawal
 */
public interface CustomerEmailVerificationDao extends CrudRepository<CustomerEmailVerification, Long> {

}
