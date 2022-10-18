/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.dao;

import com.userservice.customer.model.Address;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author z0043uwn
 */
public interface AddressDao extends CrudRepository<Address, Long> {
    
}
