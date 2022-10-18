/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.mapper;

import com.catalog.model.Category;
import com.catalog.model.Product;
import com.catalog.model.ProductImage;
import com.catalog.model.RentalBook;
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
public class RentalBookMapper implements ResultSetExtractor<Map<String, Product>> {

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
                Long rentalBookId = rs.getLong("rental_book_id");
                RentalBook rentalBook = new RentalBook();
                product.setRentalBook(rentalBook);
                rentalBook.setRentalBookId(rentalBookId);
                rentalBook.setAuthor(rs.getString("author"));
                rentalBook.setPublisher(rs.getString("publisher"));
                rentalBook.setContributor(rs.getString("contributor"));
                rentalBook.setEditor(rs.getString("editor"));
                rentalBook.setIllustrator(rs.getString("illustrator"));
                rentalBook.setDepreciation(rs.getDouble("depreciation"));
                rentalBook.setIsbn(rs.getString("isbn"));
                rentalBook.setLanguage(rs.getString("language"));
                rentalBook.setLongDescription(rs.getString("long_description"));
                if (rs.getDate("publication_date") != null) {
                    rentalBook.setPublicationDate(rs.getDate("publication_date").toLocalDate());
                }
                rentalBook.setPublisherCity(rs.getString("publisher_city"));
                rentalBook.setReviewer(rs.getString("reviewer"));
                rentalBook.setShortDescription(rs.getString("short_description"));
                rentalBook.setShortTitle(rs.getString("short_title"));
                rentalBook.setSubtitle(rs.getString("subtitle"));
                rentalBook.setTagLine(rs.getString("tag_line"));
                rentalBook.setTitle(rs.getString("title"));
                rentalBook.setTranslator(rs.getString("translator"));
                Long productImageId = rs.getLong("product_image_id");

                if (0x0 != productImageId) {
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
