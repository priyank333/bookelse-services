/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.mapper;

import com.catalog.model.BookSet;
import com.catalog.model.BookSetItem;
import com.catalog.model.Category;
import com.catalog.model.Course;
import com.catalog.model.Product;
import com.catalog.model.ProductImage;
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
public class BookSetMapper implements ResultSetExtractor<Map<String, Product>> {

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
                Long setId = rs.getLong("set_id");
                BookSet bookSet = new BookSet();

                bookSet.setSetId(setId);
                bookSet.setSemester(rs.getInt("semester"));
                bookSet.setUniversity(new University(rs.getLong("university_id"), rs.getString("university_name")));
                Set<BookSetItem> bookSetItems = new HashSet<>();
                BookSetItem bookSetItem = getBookSetItem(rs);
                bookSetItems.add(bookSetItem);
                bookSet.setBooks(bookSetItems);
                product.setBookSet(bookSet);
                List<Course> courseList = new ArrayList<>();
                courseList.add(new Course(rs.getLong("course_id"), rs.getString("course_name")));
                bookSet.setCourses(courseList);

                Long productImageId = rs.getLong("product_image_id");

                if (productImageId != 0x0) {
                    List<ProductImage> productImages = new ArrayList<>();
                    productImages.add(
                            new ProductImage(productImageId, rs.getString("imageurl"), rs.getString("image_name")));
                    product.setProductImages(productImages);
                }
                product.setCategory(new Category(rs.getLong("category_id"), rs.getString("category")));
            } else {
                Long setId = rs.getLong("set_id");
                if (Objects.equals(product.getBookSet().getSetId(), setId)) {

                    List<Course> courseList = product.getBookSet().getCourses();
                    Long courseId = rs.getLong("course_id");
                    Course course = new Course(courseId);
                    if (!courseList.contains(course)) {
                        course.setCourseName(rs.getString("course_name"));
                        courseList.add(course);
                    }
                    Set<BookSetItem> bookSetItems = product.getBookSet().getBooks();
                    BookSetItem bookSetItem = getBookSetItem(rs);
                    bookSetItems.add(bookSetItem);
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

    private BookSetItem getBookSetItem(ResultSet rs) throws SQLException {
        BookSetItem bookSetItem = new BookSetItem();
        bookSetItem.setSetItemId(rs.getLong("set_item_id"));
        bookSetItem.setAuthor(rs.getString("author"));
        bookSetItem.setPublisher(rs.getString("publisher"));
        bookSetItem.setContributor(rs.getString("contributor"));
        bookSetItem.setEditor(rs.getString("editor"));
        bookSetItem.setIllustrator(rs.getString("illustrator"));
        bookSetItem.setIsbn(rs.getString("isbn"));
        bookSetItem.setLanguage(rs.getString("language"));
        bookSetItem.setLongDescription(rs.getString("long_description"));
        if (rs.getDate("publication_date") != null) {
            bookSetItem.setPublicationDate(rs.getDate("publication_date").toLocalDate());
        }
        bookSetItem.setPublisherCity(rs.getString("publisher_city"));
        bookSetItem.setReviewer(rs.getString("reviewer"));
        bookSetItem.setShortDescription(rs.getString("short_description"));
        bookSetItem.setShortTitle(rs.getString("short_title"));
        bookSetItem.setSubtitle(rs.getString("subtitle"));
        bookSetItem.setTagLine(rs.getString("tag_line"));
        bookSetItem.setTitle(rs.getString("title"));
        bookSetItem.setTranslator(rs.getString("translator"));
        return bookSetItem;
    }

}
