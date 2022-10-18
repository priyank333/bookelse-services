/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class University implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long universityId;
    @Column(nullable = false)
    private String universityName;

    public University() {
    }

    public University(Long universityId) {
        this.universityId = universityId;
    }

    public University(String universityName) {
        this.universityName = universityName;
    }

    public University(Long universityId, String universityName) {
        this.universityId = universityId;
        this.universityName = universityName;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public String toString() {
        return "University{" + "universityId=" + universityId + ", universityName=" + universityName + '}';
    }

}
