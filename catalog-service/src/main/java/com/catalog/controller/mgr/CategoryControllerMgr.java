/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.mgr;

import com.catalog.dao.CategoryDao;
import com.catalog.model.Category;
import com.catalog.model.ServiceResponse;
import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class CategoryControllerMgr {

    @Autowired
    private CategoryDao categoryDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryControllerMgr.class);

    public ServiceResponse listAllCategories() {
        Iterable<Category> categories = categoryDao.findAll();
        LOGGER.info("Listing all categories");
        if (null != categories) {
            List<Category> categoryList = Lists.newArrayList(categories);
            return new ServiceResponse(HttpStatus.OK.value(), categoryList);
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value(), "No categories");
        }
    }

    public ServiceResponse getCategoryById(Long categoryId) {
        LOGGER.info("Getting category by id: {}", categoryId);
        Optional<Category> category = categoryDao.findById(categoryId);

        if (category.isPresent()) {
            LOGGER.info("Category is found : {} by id: {}", category.get(), categoryId);
            return new ServiceResponse(HttpStatus.OK.value(), category.get());
        } else {
            LOGGER.info("Category is not found by id: {}", categoryId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse addCategory(Category category) {
        category = categoryDao.save(category);
        LinkedHashMap<String, Object> responseParam = new LinkedHashMap<>();
        responseParam.put("categoryId", category.getCategoryId());
        LOGGER.info("Category is added with new id: {}", category.getCategoryId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteCategory(@RequestParam Long categoryId) {
        Optional<Category> category = categoryDao.findById(categoryId);
        if (!category.isPresent()) {
            LOGGER.info("In delete operation category is not found by id: {}", categoryId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value(), "Category is not found");
        }
        categoryDao.delete(new Category(categoryId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateCategory(Category category) {
        if (null != categoryDao.save(category)) {
            LOGGER.info("Category is updated with new value: {}", category);
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

}
