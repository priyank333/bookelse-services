/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.dao;

import com.userservice.customer.model.City;
import com.userservice.customer.model.State;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Priyank Agrawal
 */
public interface CityDao extends CrudRepository<City, Long> {

    @Query("SELECT c.cityId, c.city FROM City c WHERE c.state = :state")
    public List<Object[]> findByState(@Param("state") State state);
}
