/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.admin.controller.impl;

import com.userservice.admin.dao.AdminDao;
import com.userservice.admin.model.Admin;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.userservice.admin.controller.AdminController;

/**
 *
 * @author Priyank Agrawal
 */
@RequestMapping("/admin")
@RestController
public class AdminControllerImpl implements AdminController {

    @Autowired
    private AdminDao adminDao;

    @PostMapping("/v1/register")
    @Override
    public ResponseEntity<URI> addAdmin(@RequestBody Admin admin) {
        admin = adminDao.save(admin);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{adminId}")
                .buildAndExpand(admin.getAdminId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
