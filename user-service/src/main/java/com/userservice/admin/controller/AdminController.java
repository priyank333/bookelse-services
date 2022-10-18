/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.admin.controller;

import java.net.URI;

import com.userservice.admin.model.Admin;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface AdminController {

    public ResponseEntity<URI> addAdmin(Admin admin);
}
