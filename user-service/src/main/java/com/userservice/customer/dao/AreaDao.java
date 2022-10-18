/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.dao;

import com.userservice.customer.model.Area;
import com.userservice.customer.model.City;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Priyank Agrawal
 */
public interface AreaDao extends CrudRepository<Area, Long> {

    public Boolean existsByPincode(String pincode);

    @Query("SELECT a.areaId, a.area, a.pincode FROM Area a WHERE a.city = city")
    public List<Object[]> findByCity(City city);

}
