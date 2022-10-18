/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.mapper;

import com.catalog.model.Category;
import com.catalog.model.Course;
import com.catalog.model.Product;
import com.catalog.model.ProductImage;
import com.catalog.model.RentalBookSet;
import com.catalog.model.RentalBookSetItem;
import com.catalog.model.University;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Priyank Agrawal
 */
public class RentalBookSetMapper implements ResultSetExtractor<Map<String, Product>> {

    @Override
    public Map<String, Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Product> productMap = new HashMap<>();
        while (rs.next()) {
            String productId = rs.getString("product_id");
            Product product = productMap.get(productId);
            if (product == null) {
                product = new Product();
                productMap.put(productId, product);
                product.setProductId(productId);
                product.setDiscount(rs.getDouble("discount"));
                product.setIsProductOnRent(rs.getBoolean("is_product_on_rent"));
                product.setMaxDayForReturn(rs.getInt("max_day_for_return"));
                product.setProductName(rs.getString("product_name"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setRemarks(rs.getString("remarks"));
                product.setQuantity(rs.getInt("quantity"));
                product.setReserveQuantity(rs.getInt("reserve_quantity"));
                Long rentalSetId = rs.getLong("rental_set_id");
                RentalBookSet rentalBookSet = new RentalBookSet();
                rentalBookSet.setDepreciation(rs.getDouble("depreciation"));
                rentalBookSet.setRentalSetId(rentalSetId);
                rentalBookSet.setSemester(rs.getInt("semester"));

                rentalBookSet
                        .setUniversity(new University(rs.getLong("university_id"), rs.getString("university_name")));
                Set<RentalBookSetItem> rentalBookSetItems = new HashSet<>();
                RentalBookSetItem rentalBookSetItem = getRentalBookSetItem(rs);
                rentalBookSetItems.add(rentalBookSetItem);
                rentalBookSet.setRentalBooks(rentalBookSetItems);
                List<Course> courseList = new ArrayList<>();
                courseList.add(new Course(rs.getLong("course_id"), rs.getString("course_name")));
                rentalBookSet.setCourses(courseList);
                product.setRentalBookSet(rentalBookSet);
                Long productImageId = rs.getLong("product_image_id");

                if (productImageId != 0x0) {
                    List<ProductImage> productImages = new ArrayList<>();
                    productImages.add(
                            new ProductImage(productImageId, rs.getString("imageurl"), rs.getString("image_name")));
                    product.setProductImages(productImages);
                }
                product.setCategory(new Category(rs.getLong("category_id"), rs.getString("category")));
            } else {
                Long rentalSetId = rs.getLong("rental_set_id");
                if (Objects.equals(product.getRentalBookSet().getRentalSetId(), rentalSetId)) {

                    List<Course> courseList = product.getRentalBookSet().getCourses();
                    Long courseId = rs.getLong("course_id");
                    Course course = new Course(courseId);
                    if (!courseList.contains(course)) {
                        course.setCourseName(rs.getString("course_name"));
                        courseList.add(course);
                    }
                    Set<RentalBookSetItem> rentalBookSetItems = product.getRentalBookSet().getRentalBooks();
                    RentalBookSetItem rentalBookSetItem = getRentalBookSetItem(rs);
                    rentalBookSetItems.add(rentalBookSetItem);
                }
                List<ProductImage> productImages = product.getProductImages();
                if (productImages != null) {
                    Long productImageId = rs.getLong("product_image_id");
                    ProductImage productImage = new ProductImage(productImageId);
                    if (!productImages.contains(productImage)) {
                        productImage.setImageName(rs.getString("image_name"));
                        productImage.setImageURL(rs.getString("imageurl"));
                        productImages.add(productImage);
                    }
                }
            }
        }
        return productMap;
    }

    private RentalBookSetItem getRentalBookSetItem(ResultSet rs) throws SQLException {
        RentalBookSetItem rentalBookSetItem = new RentalBookSetItem();
        rentalBookSetItem.setRentalSetItemId(rs.getLong("rental_set_item_id"));
        rentalBookSetItem.setAuthor(rs.getString("author"));
        rentalBookSetItem.setPublisher(rs.getString("publisher"));
        rentalBookSetItem.setContributor(rs.getString("contributor"));
        rentalBookSetItem.setEditor(rs.getString("editor"));
        rentalBookSetItem.setIllustrator(rs.getString("illustrator"));
        rentalBookSetItem.setIsbn(rs.getString("isbn"));
        rentalBookSetItem.setLanguage(rs.getString("language"));
        rentalBookSetItem.setLongDescription(rs.getString("long_description"));
        if (rs.getDate("publication_date") != null) {
            rentalBookSetItem.setPublicationDate(rs.getDate("publication_date").toLocalDate());
        }
        rentalBookSetItem.setPublisherCity(rs.getString("publisher_city"));
        rentalBookSetItem.setReviewer(rs.getString("reviewer"));
        rentalBookSetItem.setShortDescription(rs.getString("short_description"));
        rentalBookSetItem.setShortTitle(rs.getString("short_title"));
        rentalBookSetItem.setSubtitle(rs.getString("subtitle"));
        rentalBookSetItem.setTagLine(rs.getString("tag_line"));
        rentalBookSetItem.setTitle(rs.getString("title"));
        rentalBookSetItem.setTranslator(rs.getString("translator"));
        return rentalBookSetItem;
    }

}
