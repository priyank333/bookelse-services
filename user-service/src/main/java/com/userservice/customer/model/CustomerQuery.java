/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class CustomerQuery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", updatable = false)
    private Long queryId;
    @NotBlank
    @Column(nullable = false)
    private String firstName;
    @NotBlank
    @Column(nullable = false)
    private String lastName;
    @NotBlank
    @Column(nullable = false)
    private String contact;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String emailId;
    @NotBlank
    @Column(nullable = false, length = 5000)
    private String query;
    private LocalDateTime capturedOn;

    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public LocalDateTime getCapturedOn() {
        return capturedOn;
    }

    public void setCapturedOn(LocalDateTime capturedOn) {
        this.capturedOn = capturedOn;
    }

    @Override
    public String toString() {
        return "CustomerQuery{" + "queryId=" + queryId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", contact=" + contact + ", emailId=" + emailId + ", query=" + query + ", capturedOn=" + capturedOn
                + '}';
    }

}
