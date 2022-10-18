/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.cron;

import com.coms.constant.Constants;
import com.coms.controller.mgr.RentalProductControllerMgr;
import com.coms.dao.CustomerOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.coms.dao.RentalProductDao;
import com.coms.repository.SoldProductRepository;
import com.coms.service.ProductService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author z0043uwn
 */
@Component
public class CancelPendingOrder {

    @Autowired
    private CustomerOrderDao customerOrderDao;

    @Autowired
    private RentalProductDao productsToBeReturnedByCustomerDao;

    @Autowired
    private SoldProductRepository soldProductRepository;

    @Autowired
    private ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(
            RentalProductControllerMgr.class);

    @Scheduled(fixedDelay = 100000)
    public void deletePendingOrders() {
        List<String> pendingOrders = customerOrderDao.
                getPendingOrdersForLastMinutes(
                        Constants.TIME_PERIOD_FOR_PENDING_ORDERS);
        LOGGER.info("Pending Orders :: {}", pendingOrders);
        
        if (!pendingOrders.isEmpty()) {
            Map<String, Integer> pendingProductList = soldProductRepository.
                    countProductsByOrder(pendingOrders);
            productService.releaseQuantity(pendingProductList);
            productsToBeReturnedByCustomerDao.deleteByCustomerOrders(
                    pendingOrders);
            soldProductRepository.deleteSoldProductByOrder(pendingOrders);
            customerOrderDao.deleteByCustomerOrders(pendingOrders);
        }
    }
}
