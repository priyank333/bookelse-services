/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userservice.customer.model;

import java.util.Map;

/**
 *
 * @author Priyank Agrawal
 */
public class Mail {

    private String from;
    private String to;
    private String subject;
    private Map<String, Object> model;

    public Mail() {
    }

    public Mail(String to, String subject, Map<String, Object> model) {
        this.to = to;
        this.subject = subject;
        this.model = model;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Mail{" + "from=" + from + ", to=" + to + ", subject=" + subject + ", model=" + model + '}';
    }

}
