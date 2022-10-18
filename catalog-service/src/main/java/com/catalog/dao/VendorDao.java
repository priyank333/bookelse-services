/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.dao;

import com.catalog.model.Vendor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Priyank Agrawal
 */
public interface VendorDao extends CrudRepository<Vendor, Long> {

}
