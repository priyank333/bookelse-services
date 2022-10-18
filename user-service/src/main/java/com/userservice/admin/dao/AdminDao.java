/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.admin.dao;

import com.userservice.admin.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Priyank Agrawal
 */
public interface AdminDao extends CrudRepository<Admin, Long> {

        @Query("SELECT a.adminId from Admin a " + "WHERE a.username = :username AND a.password = :password")
        public String getIdByUsernameAndPassword(@Param("username") String username,
                        @Param("password") String password);

}
