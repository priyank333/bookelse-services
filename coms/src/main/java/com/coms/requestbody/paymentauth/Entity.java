/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.requestbody.paymentauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author z0043uwn
 */
public class Entity {

    @JsonProperty("id")
    private String id;
    @JsonProperty("entity")
    private String entity;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("status")
    private String status;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("invoice_id")
    private String invoiceId;
    @JsonProperty("international")
    private String international;
    @JsonProperty("method")
    private String method;
    @JsonProperty("amount_refunded")
    private String amountRefunded;
    @JsonProperty("refund_status")
    private String refundStatus;
    @JsonProperty("captured")
    private Boolean captured;
    @JsonProperty("description")
    private String description;
    @JsonProperty("card_id")
    private String cardId;
    @JsonProperty("card")
    private Card card;
    @JsonProperty("bank")
    private String bank;
    @JsonProperty("wallet")
    private String wallet;
    @JsonProperty("vpa")
    private String vpa;
    @JsonProperty("email")
    private String email;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("notes")
    private List<String> notes;
    @JsonProperty("fee")
    private String fee;
    @JsonProperty("tax")
    private String tax;
    @JsonProperty("error_code")
    private String errorCode;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("error_source")
    private String errorSource;
    @JsonProperty("error_step")
    private String errorStep;
    @JsonProperty("error_reason")
    private String errorReason;
    @JsonProperty("acquirer_data")
    private AcquirerData acquirerData;
    @JsonProperty("created_at")
    private Long createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInternational() {
        return international;
    }

    public void setInternational(String international) {
        this.international = international;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(String amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }

    public String getErrorStep() {
        return errorStep;
    }

    public void setErrorStep(String errorStep) {
        this.errorStep = errorStep;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public AcquirerData getAcquirerData() {
        return acquirerData;
    }

    public void setAcquirerData(AcquirerData acquirerData) {
        this.acquirerData = acquirerData;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Entity{" + "id=" + id + ", entity=" + entity + ", amount=" + amount + ", currency=" + currency + ", status=" + status + ", orderId=" + orderId + ", invoiceId=" + invoiceId + ", international=" + international + ", method=" + method + ", amountRefunded=" + amountRefunded + ", refundStatus=" + refundStatus + ", captured=" + captured + ", description=" + description + ", cardId=" + cardId + ", card=" + card + ", bank=" + bank + ", wallet=" + wallet + ", vpa=" + vpa + ", email=" + email + ", contact=" + contact + ", notes=" + notes + ", fee=" + fee + ", tax=" + tax + ", errorCode=" + errorCode + ", errorDescription=" + errorDescription + ", errorSource=" + errorSource + ", errorStep=" + errorStep + ", errorReason=" + errorReason + ", acquirerData=" + acquirerData + ", createdAt=" + createdAt + '}';
    }

}
