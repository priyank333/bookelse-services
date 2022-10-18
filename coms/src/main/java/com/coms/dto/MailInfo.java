/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author z0043uwn
 */
public class MailInfo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> ccAddressList;
    private List<String> toAddressList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mailSubject;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> replayAddressList;

    public List<String> getCcAddressList() {
        return ccAddressList;
    }

    public void setCcAddressList(List<String> ccAddressList) {
        this.ccAddressList = ccAddressList;
    }

    public List<String> getToAddressList() {
        return toAddressList;
    }

    public void setToAddressList(List<String> toAddressList) {
        this.toAddressList = toAddressList;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public List<String> getReplayAddressList() {
        return replayAddressList;
    }

    public void setReplayAddressList(List<String> replayAddressList) {
        this.replayAddressList = replayAddressList;
    }

    @Override
    public String toString() {
        return "MailInfoDTO{" + "ccAddressList=" + ccAddressList + ", toAddressList=" + toAddressList + ", mailSubject=" + mailSubject + ", replayAddressList=" + replayAddressList + '}';
    }

}
