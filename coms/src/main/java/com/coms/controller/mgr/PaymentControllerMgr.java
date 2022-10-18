/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.mgr;

import com.coms.config.razorpay.RazorpayServiceConfig;
import com.coms.dao.CustomerOrderDao;
import com.coms.dto.ServiceResponse;
import com.coms.exception.OperationError;
import com.coms.exception.SecurityErrorException;
import com.coms.model.PaymentStatus;
import com.coms.requestbody.paymentauth.Entity;
import com.coms.requestbody.paymentauth.Payload;
import com.coms.requestbody.paymentauth.PaymentPayload;
import static com.coms.util.Constant.CARD_PAYMENT;
import static com.coms.util.Constant.NETBANKING_PAYMENT;
import static com.coms.util.Constant.PAYMENT_AUTHORIZED_EVENT;
import static com.coms.util.Constant.PAYMENT_CAPTURED_EVENT;
import static com.coms.util.Constant.PAYMENT_FAILED_EVENT;
import static com.coms.util.Constant.UPI_PAYMENT;
import static com.coms.util.Constant.WALLET_PAYMENT;
import static com.coms.util.OrderServiceUtil.parseJSON;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author z0043uwn
 */
@Service
public class PaymentControllerMgr {

    @Autowired
    private RazorpayServiceConfig razorpayServiceConfig;

    @Autowired
    private CustomerOrderDao customerOrderDao;

    @Autowired
    private OrderControllerMgr orderControllerMgr;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            PaymentControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse processPayment(
            String requestBody,
            String razorpaySignature) {
        PaymentPayload paymentPayload = parseJSON(requestBody,
                PaymentPayload.class);
        paymentValidation(requestBody, razorpaySignature);
        Payload payload = paymentPayload.getPayload();
        if (payload != null
                && payload.getPayment() != null
                && payload.getPayment().getEntity() != null) {
            Entity entity = payload.getPayment().getEntity();
            String paymentMode = "";
            String rrn = "";
            String authCode = "";
            String bankTransactionId = "";
            String upiTransactionId = "";
            switch (entity.getMethod()) {
                case NETBANKING_PAYMENT:
                    bankTransactionId = entity.
                            getAcquirerData().getBankTransactionId();
                    paymentMode = getNBPayMode(entity);
                    break;
                case CARD_PAYMENT:
                    authCode = entity.getAcquirerData().getAuthCode();
                    paymentMode = getCardPayMode(entity);
                    break;
                case WALLET_PAYMENT:
                    paymentMode = getWalletPayMode(entity);
                    break;
                case UPI_PAYMENT:
                    rrn = entity.getAcquirerData().getRrn();
                    upiTransactionId = entity.
                            getAcquirerData().getUpiTransactionId();
                    paymentMode = getUPIPayMode(entity);
                    break;
                default:
                    break;
            }

            takeActionBasedOnEvent(
                    paymentPayload,
                    entity,
                    bankTransactionId,
                    rrn,
                    authCode,
                    upiTransactionId,
                    paymentMode, entity.getOrderId());
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            throw new OperationError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Not received required inputs",
                    "Payment Validation");
        }
    }

    private String getUPIPayMode(Entity entity) {
        String paymentMode;
        paymentMode = entity.getMethod().toUpperCase()
                .concat("/")
                .concat(entity.getVpa().toUpperCase());
        return paymentMode;
    }

    private String getWalletPayMode(Entity entity) {
        String paymentMode;
        paymentMode = entity.getMethod().toUpperCase()
                .concat("/")
                .concat(entity.getWallet().toUpperCase());
        return paymentMode;
    }

    private String getCardPayMode(Entity entity) {
        String paymentMode;
        paymentMode = entity.getMethod().toUpperCase()
                .concat("/")
                .concat(entity.getCard().getType().toUpperCase())
                .concat("/")
                .concat(entity.getCard().getNetwork().toUpperCase());
        return paymentMode;
    }

    private String getNBPayMode(Entity entity) {
        String paymentMode;
        paymentMode = entity.getMethod().toUpperCase()
                .concat("/")
                .concat(entity.getBank().toUpperCase());
        return paymentMode;
    }

    private void paymentValidation(
            String requestBody,
            String razorpaySignature) throws SecurityErrorException {
        try {
            Boolean isValidRequest = Utils.verifyWebhookSignature(
                    requestBody,
                    razorpaySignature,
                    razorpayServiceConfig.paymentSecretKey);
            if (!isValidRequest) {
                throw new SecurityErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid Razorpay Signature",
                        "Payment Validation");
            }
        } catch (RazorpayException ex) {
            LOGGER.error("RazorpayException :: {}", ex.getCause());
        }
    }

    private void takeActionBasedOnEvent(
            PaymentPayload paymentPayload,
            Entity entity,
            String bankTransactionId,
            String rrn,
            String authCode,
            String upiTransactionId,
            String paymentMode,
            String orderId) {
        switch (paymentPayload.getEvent()) {
            case PAYMENT_AUTHORIZED_EVENT:
                customerOrderDao.updatePaymentStatus(
                        entity.getOrderId(),
                        PaymentStatus.AUTHORIZED,
                        bankTransactionId,
                        rrn,
                        authCode,
                        upiTransactionId,
                        entity.getId(),
                        Boolean.FALSE,
                        "",
                        "",
                        "",
                        "",
                        "",
                        paymentMode);
                orderControllerMgr.processOnlinePaidOrder(orderId);
                break;
            case PAYMENT_CAPTURED_EVENT:
                customerOrderDao.updatePaymentStatus(
                        entity.getOrderId(),
                        PaymentStatus.CAPTURED,
                        bankTransactionId,
                        rrn,
                        authCode,
                        upiTransactionId,
                        entity.getId(),
                        Boolean.TRUE,
                        "",
                        "",
                        "",
                        "",
                        "",
                        paymentMode);
                orderControllerMgr.processOnlinePaidOrder(orderId);
                break;
            case PAYMENT_FAILED_EVENT:
                customerOrderDao.updatePaymentStatus(
                        entity.getOrderId(),
                        PaymentStatus.FAILED,
                        bankTransactionId,
                        rrn,
                        authCode,
                        upiTransactionId,
                        entity.getId(),
                        Boolean.FALSE,
                        entity.getErrorCode(),
                        entity.getErrorDescription(),
                        entity.getErrorSource(),
                        entity.getErrorStep(),
                        entity.getErrorReason(),
                        paymentMode);
                break;
            default:
                break;
        }
    }
}
