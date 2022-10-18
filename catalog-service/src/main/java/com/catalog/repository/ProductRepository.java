/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.repository;

import com.catalog.config.sql.SqlQueries;
import com.catalog.mapper.BookMapper;
import com.catalog.mapper.BookSetMapper;
import com.catalog.mapper.RentalBookMapper;
import com.catalog.mapper.RentalBookSetMapper;
import com.catalog.model.Product;
import static com.catalog.repository.util.RepositoryUtil.setAmountRange;
import static com.catalog.repository.util.RepositoryUtil.setArgsForIn;
import static com.catalog.repository.util.RepositoryUtil.setLike;
import static com.catalog.repository.util.RepositoryUtil.setValue;
import com.catalog.requestpayload.ProductAttributePayload;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Priyank Agrawal
 */
@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlQueries sqlQueries;

    public List<Product> listRentalBook(ProductAttributePayload productAttributePayload) {

        String sqlQuery = sqlQueries.listAllRentalBookProducts
                + generateWhereConditionForRentalBooks(productAttributePayload);
        Map<String, Product> products = jdbcTemplate.query(sqlQuery, new RentalBookMapper());
        if (products != null) {
            Collection<Product> productCollection = products.values();
            return productCollection.stream().collect(toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    public List<Product> listRentalBookSets(ProductAttributePayload productAttributePayload) {

        String sqlQuery = sqlQueries.listAllRentalBookSetProducts
                + generateWhereConditionForRentalBookSet(productAttributePayload);
        Map<String, Product> products = jdbcTemplate.query(sqlQuery, new RentalBookSetMapper());
        if (products != null) {
            Collection<Product> productCollection = products.values();
            return productCollection.stream().collect(toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    public List<Product> listBookSet(ProductAttributePayload productAttributePayload) {
        String sqlQuery = sqlQueries.listAllBookSetProducts
                .concat(generateWhereConditionForBookSet(productAttributePayload));
        Map<String, Product> products = jdbcTemplate.query(sqlQuery, new BookSetMapper());
        if (products != null) {
            Collection<Product> productCollection = products.values();
            return productCollection.stream().collect(toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }

    public List<Product> listBooks(ProductAttributePayload productAttributePayload) {
        String sqlQuery = sqlQueries.listAllBookProducts
                + generateWhereConditionForBook(productAttributePayload);
        Map<String, Product> products = jdbcTemplate.query(sqlQuery, new BookMapper());
        if (products != null) {
            Collection<Product> productCollection = products.values();
            return productCollection.stream().collect(toCollection(ArrayList::new));
        }
        return Collections.emptyList();
    }
    
    private String generateWhereConditionForRentalBooks(ProductAttributePayload productAttributePayload) {
        StringBuilder whereConditions = new StringBuilder();
        generateWhereConditionForProduct(productAttributePayload, whereConditions);
        if (productAttributePayload.rentalBookAttribute != null) {
            String author = productAttributePayload.rentalBookAttribute.author;
            String publisher = productAttributePayload.rentalBookAttribute.publisher;
            String title = productAttributePayload.rentalBookAttribute.title;
            if (author != null) {
                String trimmedValue = author.trim();
                if (!trimmedValue.isEmpty()) {
                    setLike(trimmedValue, "rb", "author", whereConditions);
                }
            }
            if (publisher != null) {
                String trimmedValue = publisher.trim();
                if (!trimmedValue.isEmpty()) {
                    setLike(trimmedValue, "rb", "publisher",
                            whereConditions);
                }
            }
            if (title != null) {
                String trimmedValue = title.trim();
                if (!trimmedValue.isEmpty()) {
                    setLike(trimmedValue, "rb", "title", whereConditions);
                }
            }
        }
        return whereConditions.toString();
    }

    private void generateWhereConditionForProduct(ProductAttributePayload productAttributePayload,
            StringBuilder whereConditions) {
        String productId = productAttributePayload.productId;
        String productName = productAttributePayload.productName;
        List categories = productAttributePayload.categories;
        Double lowestDiscount = productAttributePayload.lowestDiscount;
        Double highestDiscount = productAttributePayload.highestDiscount;
        Double lowestPrice = productAttributePayload.lowestDiscount;
        Double highestPrice = productAttributePayload.highestPrice;
        if (productId != null) {
            String trimmedValue = productId.trim();
            if (!trimmedValue.isEmpty()) {
                setValue(trimmedValue, "p", "product_id", whereConditions);
            }
        }
        if (productName != null) {
            String trimmedValue = productName.trim();
            if (!trimmedValue.isEmpty()) {
                setLike(trimmedValue, "p", "product_name", whereConditions);
            }
        }
        if (categories != null && !categories.isEmpty()) {
            setArgsForIn(categories, "c", "category", whereConditions);
        }
        if (lowestDiscount != null && highestDiscount != null) {
            setAmountRange(lowestDiscount, highestDiscount, "p",
                    "discount", whereConditions);
        }
        if (lowestPrice != null && highestPrice != null) {
            setAmountRange(lowestPrice, highestPrice, "p",
                    "product_price", whereConditions);
        }

    }

    private String generateWhereConditionForBook(ProductAttributePayload productAttributePayload) {
        StringBuilder whereConditions = new StringBuilder();
        generateWhereConditionForProduct(productAttributePayload, whereConditions);
        if (productAttributePayload.bookAttribute != null) {
            String author = productAttributePayload.bookAttribute.author;
            String publisher = productAttributePayload.bookAttribute.publisher;
            String title = productAttributePayload.bookAttribute.title;
            if (author != null) {
                String trimmedValue = author.trim();
                if (!trimmedValue.isEmpty()) {
                    setLike(trimmedValue, "b", "author", whereConditions);
                }
            }
            if (publisher != null) {
                String trimmedValue = publisher.trim();
                if (!trimmedValue.isEmpty()) {
                    setLike(trimmedValue, "b", "publisher", whereConditions);
                }
            }
            if (title != null) {
                String trimmedValue = title.trim();
                if (!trimmedValue.isEmpty()) {
                    setLike(trimmedValue, "b", "title", whereConditions);
                }
            }
        }
        return whereConditions.toString();
    }

    private String generateWhereConditionForBookSet(ProductAttributePayload productAttributePayload) {
        StringBuilder whereConditions = new StringBuilder();
        generateWhereConditionForProduct(productAttributePayload, whereConditions);
        if (productAttributePayload.bookSetAttribute != null) {
            List courses = productAttributePayload.bookSetAttribute.courses;
            List semesters = productAttributePayload.bookSetAttribute.semesters;
            List universities = productAttributePayload.bookSetAttribute.universities;
            if (courses != null && (!courses.isEmpty())) {
                setArgsForIn(courses, "co", "course_name",
                        whereConditions);
            }
            if (semesters != null && (!semesters.isEmpty())) {
                setArgsForIn(semesters, "bs", "semester",
                        whereConditions);
            }
            if (universities != null && (!universities.isEmpty())) {
                setArgsForIn(universities, "u", "university_name",
                        whereConditions);
            }
        }
        return whereConditions.toString();
    }

    private String generateWhereConditionForRentalBookSet(ProductAttributePayload productAttributePayload) {
        StringBuilder whereConditions = new StringBuilder();
        generateWhereConditionForProduct(productAttributePayload, whereConditions);
        if (productAttributePayload.rentalBookSetAttribute != null) {
            List courses = productAttributePayload.rentalBookSetAttribute.courses;
            List semesters = productAttributePayload.rentalBookSetAttribute.semesters;
            List universities = productAttributePayload.rentalBookSetAttribute.universities;
            if (courses != null && (!courses.isEmpty())) {
                setArgsForIn(courses, "co", "course_name",
                        whereConditions);
            }
            if (semesters != null && (!semesters.isEmpty())) {
                setArgsForIn(semesters, "rbs", "semester",
                        whereConditions);
            }
            if (universities != null && (!universities.isEmpty())) {
                setArgsForIn(universities, "u",
                        "university_name", whereConditions);
            }

        }
        return whereConditions.toString();
    }
}
