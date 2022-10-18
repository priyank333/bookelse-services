/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.util;

import com.coms.dto.ProductDTO;
import com.coms.dto.Customer;
import com.coms.model.CustomerOrder;
import com.coms.dto.Draft;
import com.coms.dto.MailDTO;
import com.coms.dto.ProductsInDraft;
import com.coms.model.SoldProduct;
import com.coms.dto.StockItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author z0043uwn
 */
public class OrderServiceUtil {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final Logger LOGGER = LoggerFactory.getLogger(
            OrderServiceUtil.class);
    public static final NumberFormat FORMATTER = new DecimalFormat("#0.0");
    private static final int NUMBER_LENGTH_ORDER = 8;
    
    private static final String HASH_SALT_VALUE = "lakQE3@Dxp2*sx))ALPo";

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static double countProductsPrice(List<ProductsInDraft> products) {
        double finalAmount = 0.0;
        for (ProductsInDraft product : products) {
            double price = product.getSoldProduct().getSellPrice()
                    * (1 - (product.getSoldProduct().getDiscount() / 100));
            price = price * product.getQuantity();
            finalAmount += price;
        }
        return formatDecimal(finalAmount);
    }

    public static Boolean convertJSONToBoolean(String jsonInput) {
        return JsonIterator.deserialize(jsonInput).get("response").toBoolean();
    }

    public static Customer convertJsonToCustomerObj(String jsonInput) {
        Customer customer = new Customer();

        try {
            JsonNode customerNode = OBJECT_MAPPER.readTree(jsonInput).
                    get("response");
            customer.setFirstName(customerNode.get("firstName").asText());
            customer.setLastName(customerNode.get("lastName").asText());
            customer.setCustomerId(customerNode.get("customerId").asText());
            customer.setEmailId(customerNode.get("emailId").asText());
            customer.setContact(customerNode.get("contact").asText());
            customer.setIsEmailIdVerified(customerNode.get("isEmailVerified").
                    asBoolean());
        } catch (JsonProcessingException ex) {

        }
        return customer;
    }

    public static double calculateDeposite(double amount, double depreciation,
            int period) {
        LOGGER.info("Amount :: {}, depreciation :: {}, period :: {}", amount, depreciation, period);
        depreciation *= period;
        double deposite = amount * (1 - (depreciation / 100));
        return formatDecimal(deposite);
    }

    public static Draft convertJsonToDraft(String jsonInput) {
        Draft draft = null;
        try {
            draft = OBJECT_MAPPER.convertValue(
                    OBJECT_MAPPER.readTree(jsonInput).get("response"),
                    new TypeReference<Draft>() {
            });
        } catch (JsonProcessingException | IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage());
        }
        return draft;
    }

    public static Map<Long, Integer> retrieveProductQuantity(String jsonInput) {
        Map<Long, Integer> productsList = new HashMap<>();
        for (Any product : JsonIterator.deserialize(jsonInput).
                get("productsList").asList()) {
            productsList.put(product.get("productId").toLong(),
                    product.get("quantity").toInt());
        }
        return productsList;
    }

    public static List<StockItem> jsonToStockItems(
            String jsonInput) {
        List<StockItem> stockList = new ArrayList<>();
        List<Any> jsonStockList = JsonIterator.deserialize(jsonInput).
                get("response").asList();
        for (Any jsonStockItem : jsonStockList) {
            StockItem stockItem = new StockItem();
            stockItem.setStockItemId(jsonStockItem.get("stockItemId").toLong());
            stockItem.setVendorId(jsonStockItem.get("vendor").
                    get("vendorId").toLong());
            stockItem.setPurchasedPrice(jsonStockItem.get("purchasedPrice")
                    .toDouble());
            stockItem.setPurchasedOn(LocalDate.parse(jsonStockItem.
                    get("purchasedOn").toString()));
            stockList.add(stockItem);
        }
        return stockList;
    }

    public static String retrieveEmailAddressFromJson(String jsonResponse) {
        if (jsonResponse != null) {
            return JsonIterator.deserialize(jsonResponse).get("response").
                    get("emailAddress").as(String.class);
        } else {
            return null;
        }
    }

    public static MailDTO getOrderInfo(
            String customerMailAddress,
            CustomerOrder customerOrder,
            Map<String, List<SoldProduct>> groupByProduct) {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setEmailAddress(customerMailAddress);
        mailDTO.setOrderNumber(customerOrder.getOrderNumber());
        mailDTO.setPayableAmount(customerOrder.getFinalAmount());
        mailDTO.setOrderDate(customerOrder.getOrderedDate().
                toLocalDateTime().toLocalDate());
        Customer customer = customerOrder.getCustomer();
        mailDTO.setFirstName(customer != null ? customer.getFirstName() : "");
        mailDTO.setLastName(customer != null ? customer.getLastName() : "");
        mailDTO.setPaymentMode(customerOrder.getPaymentMode() != null
                ? customerOrder.getPaymentMode().toUpperCase() : "");
        mailDTO.setShippingCharge(customerOrder.getShippingCharge());
        List<ProductDTO> orderedProducts = new ArrayList<>();
        mailDTO.setProductList(orderedProducts);
        for (Map.Entry<String, List<SoldProduct>> soldProduct
                : groupByProduct.entrySet()) {
            ProductDTO orderedProductDTO = new ProductDTO();
            SoldProduct product = soldProduct.getValue().get(0);
            orderedProductDTO.setProductName(product.getProductName());
            int quantity = soldProduct.getValue().size();
            double netPrice = OrderServiceUtil.calculateNetPrice(
                    product.getSellPrice(), product.getDiscount());
            orderedProductDTO.setFinalPrice(netPrice);
            orderedProductDTO.setQuantity(quantity);
            orderedProductDTO.setSellPrice(product.getSellPrice());
            orderedProductDTO.setDiscount(product.getDiscount());
            orderedProducts.add(orderedProductDTO);
        }
        return mailDTO;
    }

    public static double calculateNetPrice(double productPrice, double discount) {
        double netPrice = productPrice
                * (1 - (discount / 100));

        return formatDecimal(netPrice);
    }

    public static Map<String, List<SoldProduct>> groupByProduct(
            List<SoldProduct> soldProducts) {
        return soldProducts.stream().
                collect(Collectors.groupingBy(SoldProduct::getProductId));
    }

    public static String generateOrderNumber(String customerId) {
        String orderDate = getDateTimeOnFormat("yyMMdd");
        StringBuilder orderNumber = new StringBuilder(orderDate);
        orderNumber.append("-").
                append(customerId).
                append("-").
                append(generateRandomNumericValue(NUMBER_LENGTH_ORDER));
        return orderNumber.toString();
    }

    public static String getDateTimeOnFormat(String format) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat(format);
        String datetime = ft.format(dNow);
        return datetime;
    }

    private static String generateRandomNumericValue(Integer length) {
        String randomNumber = "";
        do {
            randomNumber = RandomStringUtils.randomNumeric(length);
            randomNumber = randomNumber.replaceFirst("^0+(?!$)", "");
        } while (randomNumber.length() < length);
        return randomNumber;
    }

    public static String generateRentalProductId(
            String existRentalProductId) {
        String input = HASH_SALT_VALUE.
                concat(existRentalProductId);
        LOGGER.info("Generating rentalProductId: {}", input);
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            return DatatypeConverter.printHexBinary(messageDigest.digest()).
                    toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Exception Message : {}", ex.getMessage());
            LOGGER.error("Exception StackTrace : {}", Arrays.toString(
                    ex.getStackTrace()));
        }
        return "";
    }

    public static String generateRentalProductId(
            Long soldProductId,
            String orderNumber,
            String customerId) {
        String input = HASH_SALT_VALUE.
                concat(soldProductId.toString()).
                concat(orderNumber).
                concat(customerId);
        LOGGER.info("Generating rentalProductId: {}", input);
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            return DatatypeConverter.printHexBinary(messageDigest.digest()).
                    toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Exception Message : {}", ex.getMessage());
            LOGGER.error("Exception StackTrace : {}", Arrays.toString(
                    ex.getStackTrace()));
        }
        return "";
    }

    public static double formatDecimal(double inputNumber) {
        return Double.parseDouble(FORMATTER.format(inputNumber));
    }

    public static final <T extends Object> T parseJSON(
            String jsonValue, Class<T> classType) {

        try {
            return OBJECT_MAPPER.readValue(jsonValue, classType);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error while parsing JSON to Java :: {}", ex.getMessage());
        }
        return null;
    }

}
