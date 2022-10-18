/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.mgr;

import com.coms.dao.RentalProductDao;
import com.coms.dto.RentalProductDTO;
import com.coms.model.RentalProduct;
import com.coms.dto.ServiceResponse;
import com.coms.exception.OperationError;
import com.coms.payload.RentalProductForAdminPayload;
import com.coms.payload.RentalProductPayload;
import com.coms.repository.RentalProductRepository;
import com.coms.util.OrderServiceUtil;
import static com.coms.util.OrderServiceUtil.formatDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.SerializationUtils;
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
public class RentalProductControllerMgr {

    @Autowired
    private RentalProductRepository rentalProductRepository;

    @Autowired
    private RentalProductDao rentalProductDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            RentalProductControllerMgr.class);

    public ServiceResponse listRentalProducts(
            RentalProductPayload rentalProductPayload) {
        LOGGER.info("Listing rental product (Customer) by payload : {}",
                rentalProductPayload);
        List<RentalProductDTO> rentalProducts = rentalProductRepository.
                listRentalProducts(rentalProductPayload);
        if (rentalProducts.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            return new ServiceResponse(HttpStatus.OK.value(), rentalProducts);
        }
    }

    public ServiceResponse listRentalProducts(
            RentalProductForAdminPayload rentalProductPayload) {
        LOGGER.info("Listing rental product(Admin) by payload : {}",
                rentalProductPayload);
        List<RentalProductDTO> rentalProducts = rentalProductRepository.
                listRentalProducts(rentalProductPayload);
        if (rentalProducts.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            return new ServiceResponse(HttpStatus.OK.value(), rentalProducts);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse extendPeriod(String rentalProductId, Integer period) {
        LOGGER.info("Extending period for rentalProductId : {}, period : {}",
                rentalProductId, period);
        RentalProduct productToExtend = rentalProductRepository.
                getRentalProductForExtendPeriod(rentalProductId);

        if (productToExtend.getIsLocked()) {
            LOGGER.info("Product is locked : {} with reason : {}",
                    rentalProductId,
                    productToExtend.getLockReason());
            Map<String, Object> resource = new HashMap();
            resource.put("rentalProductId", rentalProductId);
            resource.put("period", period);
            throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Product is locked :: " + productToExtend.getLockReason(),
                    resource, "ExtendPeriod");
        } else if (!productToExtend.getIsEligibleForReturn()) {
            LOGGER.info("Period is not eligible for extend for product : {} with month : {}",
                    rentalProductId,
                    period);
            Map<String, Object> resource = new HashMap();
            resource.put("rentalProductId", rentalProductId);
            resource.put("period", period);
            throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Period cannot be extended because product is not "
                    + "eligible for return.", resource, "ExtendPeriod");
        } else if (productToExtend.getIsPeriodExtended()) {
            LOGGER.info("Period is already extended for product : {} with month : {}",
                    rentalProductId,
                    period);
            Map<String, Object> resource = new HashMap();
            resource.put("rentalProductId", rentalProductId);
            resource.put("period", period);
            throw new OperationError(HttpStatus.PRECONDITION_FAILED,
                    "Product period is already extended.", resource,
                    "ExtendPeriod");
        }
        productToExtend.setRentalProductId(rentalProductId);
        RentalProduct extendedProduct = getExtendedRentalProduct(
                productToExtend, period);
        rentalProductDao.updateProductExtend(rentalProductId, Boolean.TRUE);
        rentalProductDao.updateProductExtendedOn(rentalProductId,
                LocalDate.now());
        rentalProductDao.updateEligibilityForReturnByRPID(rentalProductId,
                Boolean.FALSE);
        boolean isExtended = rentalProductRepository.
                makeEntryInRentalProduct(extendedProduct);
        LOGGER.info("Is period extended : {} for product : {} with month : {}",
                isExtended,
                rentalProductId,
                period);
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(HttpStatus.CREATED.value());
        serviceResponse.setResponse(isExtended);
        return serviceResponse;
    }

    private RentalProduct getExtendedRentalProduct(
            RentalProduct productToExtend, Integer extendPeriod) {
        RentalProduct rentalProduct
                = SerializationUtils.clone(productToExtend);
        rentalProduct.setRentalProductId(OrderServiceUtil.
                generateRentalProductId(productToExtend.getRentalProductId()));
        rentalProduct.setDelayCharge(0.0);
        rentalProduct.setRentPeriod(productToExtend.getRentPeriod()
                + extendPeriod);
        rentalProduct.setExtendedRentPeriod(extendPeriod);
        rentalProduct.setExtendedOn(LocalDate.now());
        rentalProduct.setIsLocked(Boolean.FALSE);
        rentalProduct.setLockReason("");
        rentalProduct.setIsPeriodExtended(false);
        Double netRefundAmount = productToExtend.getRefundAmount()
                - productToExtend.getDelayCharge();
        netRefundAmount = netRefundAmount < 0 ? 0.00 : netRefundAmount;
        Double perMonthCharge = productToExtend.getRentalCharge()
                / productToExtend.getRentPeriod();
        Double extendedRentCharge = perMonthCharge * extendPeriod;
        Double deposite
                = netRefundAmount - extendedRentCharge;
        deposite = deposite < 0 ? 0 : deposite;
        if (deposite == 0) {
            rentalProduct.setIsEligibleForReturn(false);
            rentalProduct.setRentalCharge(formatDecimal(productToExtend.getRentalCharge()
                    + netRefundAmount));
            rentalProduct.setExtendedRentalCharge(formatDecimal(netRefundAmount));
            rentalProduct.setDueDate(null);
        } else {
            if (productToExtend.getDueDate().isBefore(LocalDate.now())) {
                rentalProduct.setDueDate(
                        LocalDate.now().plusMonths(extendPeriod));
            } else {
                rentalProduct.setDueDate(
                        productToExtend.getDueDate().plusMonths(extendPeriod));
            }
            rentalProduct.setIsEligibleForReturn(true);
            rentalProduct.setExtendedRentalCharge(formatDecimal(extendedRentCharge));
            rentalProduct.setRentalCharge(formatDecimal(productToExtend.getRentalCharge()
                    + extendedRentCharge));
        }
        rentalProduct.setRefundAmount(formatDecimal(deposite));
        rentalProduct.setDeposite(formatDecimal(productToExtend.getDeposite()));
        return rentalProduct;
    }

    @Transactional(rollbackFor = Exception.class)
    public void processDelayedRentalProductCharges() {
        List<RentalProductDTO> delayedRentalItems = rentalProductRepository.
                listDelayedRentalItems();
        LOGGER.info("Received delayed rental items :: {}", delayedRentalItems);
        List<RentalProduct> rentalItemsList = new ArrayList<>();
        delayedRentalItems.stream().map((delayedRentalProduct) -> {
            RentalProduct rentalProduct;
            rentalProduct = processForDelayCharge(delayedRentalProduct);
            return rentalProduct;
        }).forEachOrdered((rentalProduct) -> {
            rentalItemsList.add(rentalProduct);
        });
        LOGGER.info("Rental items with updated value :: {}", rentalItemsList);
        rentalProductRepository.updateDelayedCharges(rentalItemsList);
    }

    @Transactional(rollbackFor = Exception.class)
    private RentalProduct processForDelayCharge(
            final RentalProductDTO rentalProductDTO) {
        RentalProduct rentalProduct = new RentalProduct();
        rentalProduct.setRentalProductId(rentalProductDTO.getRentalProductId());
        Integer duesDay = rentalProductDTO.getDueDays();
        Double rentalChargeByDay = (rentalProductDTO.getRentalCharge()
                / rentalProductDTO.getRentPeriod()) / 30;
        Double delayCharge
                = (rentalChargeByDay * duesDay);
        if (delayCharge > rentalProductDTO.getRefundAmount()) {
            rentalProduct.setDelayCharge(formatDecimal(rentalProductDTO.getDeposite()));
        } else {
            rentalProduct.setDelayCharge(formatDecimal(delayCharge));
        }
        Double netRefundAmount
                = rentalProductDTO.getDeposite() - rentalProduct.getDelayCharge();
        if (netRefundAmount <= 0) {
            rentalProduct.setIsEligibleForReturn(Boolean.FALSE);
            netRefundAmount = 0.00;
        } else {
            rentalProduct.setIsEligibleForReturn(Boolean.TRUE);
        }
        rentalProduct.setRefundAmount(formatDecimal(netRefundAmount));
        return rentalProduct;
    }
}
