/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.mgr;

import com.catalog.constants.CatalogConstant;
import com.catalog.dao.DraftDao;
import com.catalog.dao.ProductDao;
import com.catalog.exception.OperationError;
import com.catalog.exception.ResourceNotFoundException;
import com.catalog.model.Draft;
import com.catalog.model.Product;
import com.catalog.model.ProductsInDraft;
import com.catalog.model.ServiceResponse;
import com.catalog.repository.DraftRepository;
import com.catalog.service.UserService;
import com.catalog.util.ProductServiceUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class DraftControllerMgr {

    @Autowired
    private DraftDao draftDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private DraftRepository draftRepo;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DraftControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateProductsInDraft(final String customerId, final String productId,
            final Integer quantity) {

        LOGGER.info("Updating draft for customer: {}, productId: {}, quantity: {}", customerId, productId, quantity);

        ServiceResponse serviceResponse;
        Integer productQuantity = productDao.getQuantity(productId);
        Boolean isExist = productDao.existsById(productId);
        if (!isExist || productQuantity == null) {
            throw new ResourceNotFoundException("product", productId, "product is not found");
        }
        if (productQuantity - quantity < 0) {
            LOGGER.info(
                    "While updating draft product is not available " + "for customer: {}, productId: {}, quantity: {}",
                    customerId, productId, quantity);
            return new ServiceResponse(HttpStatus.OK.value(), "Product is not available");
        }
        // If draft is being created a new.
        if (draftDao.countDraft(customerId) == 0) {
            if (quantity == 0) {
                LOGGER.info("Validation failed while creating draft. " + "Reason: quantity must greater than 1. "
                        + "For customer: {}, productId: {}, quantity: {}", customerId, productId, quantity);
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("customerId", customerId);
                parameters.put("productId", productId);
                parameters.put("quantity", quantity);
                throw new OperationError(HttpStatus.PRECONDITION_FAILED, "Quantity must be greater than 1", parameters,
                        "UpdateDraft");
            }
            Draft newDraft = createNewDraft(customerId, productId, quantity);
            LinkedHashMap<String, Long> draftId = new LinkedHashMap<>();
            Long newDraftId = draftDao.save(newDraft).getDraftId();
            if (newDraft != null) {
                draftId.put("draftId", newDraftId);
                LOGGER.info("New draft is created with draftId: {} for customer: {}", draftId, customerId);
                serviceResponse = new ServiceResponse(HttpStatus.CREATED.value(), draftId);
            } else {
                LOGGER.info("Error caused while creating a draft for customer: {}", customerId);
                serviceResponse = new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), draftId);
            }

        } else {
            LOGGER.info("Draft is updating for customer: {}", customerId);
            Draft draftToBeUpdated = draftRepo.getDraftByCustomerId(customerId);
            LOGGER.info("draft: {}", draftToBeUpdated);
            ProductsInDraft productsInDraft = getProductsInDraftFromDraft(productId,
                    draftToBeUpdated.getProductsInDraft());
            if (productsInDraft == null) {
                if (quantity == 0) {
                    LOGGER.info("Validation failed while updating draft. " + "Reason: quantity must greater than 1. "
                            + "For customer: {}, productId: {}, quantity: {}", customerId, productId, quantity);
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("customerId", customerId);
                    parameters.put("productId", productId);
                    parameters.put("quantity", quantity);

                    throw new OperationError(HttpStatus.PRECONDITION_FAILED, "Quantity must be greater than 1",
                            parameters, "UpdateDraft");
                }
                addProductInDraft(productId, quantity, draftToBeUpdated);
                draftDao.save(draftToBeUpdated);
                serviceResponse = new ServiceResponse(HttpStatus.OK.value(), "Product quantity is updated in a draft");
            } else {
                if (quantity == 0) {
                    LOGGER.info("Deleting product from draft " + "for customer: {}, productId: {}", customerId,
                            productId);
                    serviceResponse = deleteProductFromDraft(draftToBeUpdated, productsInDraft);
                } else {
                    LOGGER.debug("Checking....");
                    serviceResponse = updateProductQuantityInDraft(productsInDraft, quantity, draftToBeUpdated);
                }
            }
        }
        return serviceResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    private ServiceResponse updateProductQuantityInDraft(ProductsInDraft productsInDraft, final Integer quantity,
            Draft draftToBeUpdated) {
        ServiceResponse serviceResponse;
        productsInDraft.setQuantity(quantity);
        draftDao.save(draftToBeUpdated);
        serviceResponse = new ServiceResponse(HttpStatus.OK.value(), "Product quantity is updated in a draft");
        return serviceResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    private ServiceResponse deleteProductFromDraft(Draft draftToBeUpdated, ProductsInDraft productsInDraft) {
        ServiceResponse serviceResponse;
        draftToBeUpdated.getProductsInDraft().remove(productsInDraft);
        if (draftToBeUpdated.getProductsInDraft().isEmpty()) {
            draftDao.delete(draftToBeUpdated);
            serviceResponse = new ServiceResponse(HttpStatus.OK.value(), "Draft is deleted");
        } else {
            draftDao.save(draftToBeUpdated);
            serviceResponse = new ServiceResponse(HttpStatus.OK.value(), "Product is deleted from draft");
        }
        return serviceResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    private void addProductInDraft(final String productId, final Integer quantity, Draft draftToBeUpdated) {
        ProductsInDraft newProductInDraft = new ProductsInDraft();
        newProductInDraft.setProduct(new Product(productId));
        newProductInDraft.setQuantity(quantity);
        draftToBeUpdated.getProductsInDraft().add(newProductInDraft);
    }

    @Transactional(rollbackFor = Exception.class)
    private Draft createNewDraft(final String customerId, final String productId, final Integer quantity) {
        LOGGER.info("New draft is creating for customer: {}, product: {}, quantity: {}", customerId, productId,
                quantity);
        if (!userService.isAccountActive(customerId)) {
            throw new ResourceNotFoundException("customerId", customerId,
                    "Customer id is not active or not registered with us.");
        }
        Draft newDraft = new Draft();
        newDraft.setCustomerId(customerId);
        ProductsInDraft productsInDraft = new ProductsInDraft();
        productsInDraft.setProduct(new Product(productId));
        productsInDraft.setQuantity(quantity);
        newDraft.setProductsInDraft(Arrays.asList(productsInDraft));
        return newDraft;
    }

    private ProductsInDraft getProductsInDraftFromDraft(final String productId,
            List<ProductsInDraft> productsInDraftList) {
        for (ProductsInDraft productsInDraftOfDraft : productsInDraftList) {
            if (Objects.equals(productsInDraftOfDraft.getProduct().getProductId(), productId)) {
                return productsInDraftOfDraft;
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteDraft(final Long draftId) {
        LOGGER.info("Deleting draft by id: {}", draftId);
        Boolean isDraftExist = draftDao.existsById(draftId);
        if (!isDraftExist) {
            LOGGER.info("While deleting draft, " + "Draft is not exist with id: {}", draftId);
            throw new ResourceNotFoundException("draft", draftId, "Draft is not found.");

        }
        Boolean isDraftDeleted = draftDao.deleteByDraftId(draftId) == 1;
        if (isDraftDeleted) {
            LOGGER.info("Draft is deleted with id: {}", draftId);
            return new ServiceResponse(HttpStatus.OK.value(), isDraftDeleted);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), isDraftDeleted);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteDraftByCustomer(final String customerId) {
        Boolean isDraftExist = draftDao.existsByCustomerId(customerId);
        if (!isDraftExist) {
            LOGGER.info("While deleting draft by customer, " + "Draft is not exist with customerId: {}", customerId);
            throw new ResourceNotFoundException("draft", customerId, "Draft is not found by customerId.");
        }
        Boolean isDraftDeleted = draftDao.deleteByCustomerId(customerId) == 1;
        if (isDraftDeleted) {
            return new ServiceResponse(HttpStatus.OK.value(), isDraftDeleted);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), isDraftDeleted);
        }
    }

    public ServiceResponse getDraft(String customerId, Long draftId) {
        Draft draft = null;
        if (customerId != null) {
            draft = draftRepo.getDraftByCustomerId(customerId);
        } else if (draftId != null) {
            draft = draftRepo.getDraftByDraftId(draftId);
        }

        ServiceResponse serviceResponse = new ServiceResponse();
        if (draft == null) {
            serviceResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
        } else {
            System.out.println(draft);
            double totalAmount = ProductServiceUtil.getTotalAmountOfDraft(draft.getProductsInDraft());
            if (totalAmount < CatalogConstant.MIN_AMT_FOR_FREE_SHIPPING) {
                draft.setShippingCharge(CatalogConstant.SHIPPING_CHARGE);
            } else {
                draft.setShippingCharge(0.00);
            }
            serviceResponse.setStatusCode(HttpStatus.OK.value());
            serviceResponse.setResponse(draft);
        }
        return serviceResponse;
    }
}
