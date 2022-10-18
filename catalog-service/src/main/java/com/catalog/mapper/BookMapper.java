/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.mapper;

import com.catalog.model.Book;
import com.catalog.model.Category;
import com.catalog.model.Product;
import com.catalog.model.ProductImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Priyank Agrawal
 */
public class BookMapper implements ResultSetExtractor<Map<String, Product>> {

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
                Book book = new Book();

                book.setBookId(rs.getLong("book_id"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setContributor(rs.getString("contributor"));
                book.setEditor(rs.getString("editor"));
                book.setIllustrator(rs.getString("illustrator"));
                book.setIsbn(rs.getString("isbn"));
                book.setLanguage(rs.getString("language"));
                book.setLongDescription(rs.getString("long_description"));
                if (rs.getDate("publication_date") != null) {
                    book.setPublicationDate(rs.getDate("publication_date").toLocalDate());
                }
                book.setPublisherCity(rs.getString("publisher_city"));
                book.setReviewer(rs.getString("reviewer"));
                book.setShortDescription(rs.getString("short_description"));
                book.setShortTitle(rs.getString("short_title"));
                book.setSubtitle(rs.getString("subtitle"));
                book.setTagLine(rs.getString("tag_line"));
                book.setTitle(rs.getString("title"));
                book.setTranslator(rs.getString("translator"));

                product.setBook(book);
                Long productImageId = rs.getLong("product_image_id");
                if (productImageId != 0x0) {
                    List<ProductImage> productImages = new ArrayList<>();
                    productImages.add(
                            new ProductImage(productImageId, rs.getString("imageurl"), rs.getString("image_name")));
                    product.setProductImages(productImages);
                }
                product.setCategory(new Category(rs.getLong("category_id"), rs.getString("category")));
            } else {
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

}
