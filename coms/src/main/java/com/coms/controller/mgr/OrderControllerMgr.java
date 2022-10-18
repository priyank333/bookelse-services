/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.controller.mgr;

import com.coms.config.app.App;
import com.coms.config.razorpay.RazorpayServiceConfig;
import static com.coms.constant.Constants.INITIAL_RETURN_PERIOD;
import com.coms.dao.CustomerOrderDao;
import com.coms.dao.RentalProductDao;
import com.coms.dto.MailDTO;
import com.coms.dto.Customer;
import com.coms.dto.DeliveryConfirmationMail;
import com.coms.model.CustomerOrder;
import com.coms.dto.Draft;
import com.coms.dto.MailInfo;
import com.coms.dto.OnlinePaymentOrderDetails;
import com.coms.dto.OrderConfirmationMail;
import com.coms.dto.ProductsInDraft;
import com.coms.model.OrderStatus;
import com.coms.model.PaymentMode;
import com.coms.model.RentalProduct;
import com.coms.dto.ServiceResponse;
import com.coms.dto.ShippingConfirmationMail;
import com.coms.model.SoldProduct;
import com.coms.dto.StockItem;
import com.coms.exception.OperationError;
import com.coms.exception.ResourceNotFoundException;
import com.coms.payload.CustomerOrderForAdminPayload;
import com.coms.payload.CustomerOrderPayload;
import com.coms.repository.CustomerOrderRepository;
import com.coms.repository.MailInfoRepository;
import com.coms.service.MailService;
import com.coms.service.ProductService;
import com.coms.service.UserService;
import com.coms.util.OrderServiceUtil;
import static com.coms.util.OrderServiceUtil.formatDecimal;
import static com.coms.util.OrderServiceUtil.getOrderInfo;
import static com.coms.util.OrderServiceUtil.groupByProduct;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONException;
import org.json.JSONObject;
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
public class OrderControllerMgr {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private RazorpayServiceConfig razorpayServiceConfig;

    @Autowired
    private CustomerOrderDao customerOrderDao;

    @Autowired
    private RentalProductDao rentalProductDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private App appProperty;

    @Autowired
    private CustomerOrderRepository orderRepo;

    @Autowired
    private MailInfoRepository mailInfoRepository;

    private static final Logger LOGGER = LoggerFactory.
            getLogger(OrderControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public String processOrderForCash(
            String customerId,
            Long draftId,
            String billingAddress,
            String shippingAddress) {
        LOGGER.info("Draft id : {} for customer {}", draftId, customerId);
        LOGGER.info("Creating cash order for customerId : {}", customerId);
        CustomerOrder customerOrder = generateOrder(customerId, draftId);
        customerOrder.setPaymentMode("CASH");
        customerOrder.setOrderStatus(OrderStatus.RECEIVED);
        customerOrder.setPaymentIdNo("");
        customerOrder.setBillingAddress(billingAddress);
        customerOrder.setShippingAddress(shippingAddress);
        customerOrder.setPaymentOrderNumber("");
        CustomerOrder savedOrder = customerOrderDao.save(customerOrder);
        LOGGER.info("Saved Order :: {}", savedOrder);
        Map<String, List<SoldProduct>> groupByProduct = OrderServiceUtil.
                groupByProduct(savedOrder.getProductsList());
        LOGGER.info("Map Product :: {}", groupByProduct);
        List<RentalProduct> rentalProducts = getRentalProductList(savedOrder,
                customerOrder.getProductsList());
        if (!rentalProducts.isEmpty()) {
            LOGGER.info("Adding rental products in order for customer : {}.",
                    customerId);
            rentalProductDao.saveAll(rentalProducts);
        }
        LOGGER.info("isCashOrder Generated : {}", savedOrder);
        String customerEmailAddress = userService.getCustomerEmailAddress(customerId);

        updateOrderStatus(customerOrder.getOrderNumber(), OrderStatus.RECEIVED);
        MailDTO mailDTO = OrderServiceUtil.
                getOrderInfo(
                        customerEmailAddress, customerOrder, groupByProduct);
        Map<String, Object> mailBody = prepareOrderConfirmationMail(mailDTO);
        mailService.sendMailForOrderConfirmation(mailBody);
        customerOrder = deductQuantityForOrder(customerOrder, groupByProduct);
        customerOrderDao.save(customerOrder);
        deleteDraftByCustomerId(customerId);
        return customerOrder.getOrderNumber();
    }

    @Transactional(rollbackFor = Exception.class)
    public OnlinePaymentOrderDetails createOrderForOnlinePayment(
            String customerId,
            Long draftId,
            String billingAddress,
            String shippingAddress) {
        LOGGER.info("Creating order (Online Payment) for customer : {}", customerId);
        CustomerOrder customerOrder = generateOrder(customerId, draftId);
        customerOrder.setOrderStatus(OrderStatus.CREATED);
        OnlinePaymentOrderDetails onlinePaymentOrderDetails
                = getPaymentOrderNumber(customerOrder);
        customerOrder.setPaymentOrderNumber(
                onlinePaymentOrderDetails.getOrderNumberForPayment());
        customerOrder.setBillingAddress(billingAddress);
        customerOrder.setShippingAddress(shippingAddress);
        CustomerOrder savedOrder = customerOrderDao.save(customerOrder);
        List<RentalProduct> rentalProducts
                = getRentalProductList(savedOrder,
                        customerOrder.getProductsList());
        if (!rentalProducts.isEmpty()) {
            rentalProductDao.saveAll(rentalProducts);
            LOGGER.info("Saving all rental products for customer : {}",
                    customerId);
        }
        reserveQuantity(customerOrder.getProductsList());
        onlinePaymentOrderDetails.setOrderNumber(customerOrder.getOrderNumber());
        return onlinePaymentOrderDetails;
    }

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrder generateOrder(String customerId, Long draftId) {
        CustomerOrder customerOrder = new CustomerOrder();
        LOGGER.info("Generating order details for customer : {}", customerId);
        customerOrder.setOrderNumber(OrderServiceUtil.
                generateOrderNumber(customerId));
        Customer customer = userService.getCustomerDetails(customerId);
        if (customer != null) {
            customerOrder.setCustomer(customer);
            customerOrder.setCustomerId(customerId);
        }
        Draft draft = productService.getDraft(draftId);
        customerOrder.setShippingCharge(draft.getShippingCharge());
        Double finalAmount = OrderServiceUtil.
                countProductsPrice(draft.getProductsInDraft());
        List<SoldProduct> soldProducts = new ArrayList<>();
        List<ProductsInDraft> draftProducts = draft.getProductsInDraft();
        for (int productCount = 0; productCount < draft.getProductsInDraft().size(); productCount++) {
            ProductsInDraft draftProduct = draftProducts.get(productCount);
            SoldProduct soldProduct = draftProduct.getSoldProduct();
            for (int qtyCount = 0; qtyCount < draftProduct.getQuantity(); qtyCount++) {
                SoldProduct productToBeAdded = SerializationUtils.clone(soldProduct);
                productToBeAdded.setIsProductReturned(false);
                productToBeAdded.setIsReturnRequestOpen(false);
                soldProducts.add(productToBeAdded);
            }
        }
        customerOrder.setProductsList(soldProducts);
        Double finalAmtWithShipping = formatDecimal(
                finalAmount + draft.getShippingCharge());
        customerOrder.setFinalAmount(finalAmtWithShipping);
        customerOrder.setIsAmountPaid(false);
        customerOrder.setOrderedDate(Timestamp.valueOf(LocalDateTime.now()));
        return customerOrder;
    }

    @Transactional(rollbackFor = Exception.class)
    public void reserveQuantity(List<SoldProduct> products) {
        Map<String, Integer> reserveList = new HashMap<>();
        for (SoldProduct product : products) {
            Integer existingQuantity = reserveList.get(product.getProductId());
            if (existingQuantity == null) {
                reserveList.put(product.getProductId(), 1);
            } else {
                existingQuantity++;
                reserveList.put(product.getProductId(), 1);
            }
        }
        LOGGER.info("Reserve List :: {}", reserveList);
        productService.reserveQuantity(reserveList);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<RentalProduct> getRentalProductList(
            CustomerOrder customerOrder, List<SoldProduct> soldProducts) {
        List<RentalProduct> rentalProducts
                = new ArrayList<>();
        for (SoldProduct savedSoldProduct : customerOrder.getProductsList()) {
            if (savedSoldProduct.getIsProductOnRent()) {
                for (SoldProduct unsavedSoldProduct : soldProducts) {
                    if (unsavedSoldProduct.getProductId().equals(
                            savedSoldProduct.getProductId())) {
                        savedSoldProduct.setDepreciation(
                                unsavedSoldProduct.getDepreciation());
                        RentalProduct rentalProduct
                                = setRentalProducts(
                                        customerOrder, savedSoldProduct);
                        rentalProducts.add(rentalProduct);
                        break;
                    }
                }
            }
        }
        return rentalProducts;
    }

    private RentalProduct setRentalProducts(
            CustomerOrder customerOrder, SoldProduct soldProduct) {
        RentalProduct rentalProduct
                = new RentalProduct();
        rentalProduct.setRentalProductId(
                OrderServiceUtil.generateRentalProductId(
                        soldProduct.getSoldProductId(),
                        customerOrder.getOrderNumber(),
                        customerOrder.getCustomerId()));
        rentalProduct.setCustomerOrder(customerOrder);
        rentalProduct.setDelayCharge(0.0);
        rentalProduct.setDueDate(
                LocalDate.now().plusMonths(INITIAL_RETURN_PERIOD));
        rentalProduct.setInitialReturnPeriod(INITIAL_RETURN_PERIOD);
        rentalProduct.setRentPeriod(INITIAL_RETURN_PERIOD);
        rentalProduct.setRentedOn(LocalDate.now());
        rentalProduct.setIsPeriodExtended(false);
        rentalProduct.setSoldProduct(soldProduct);
        rentalProduct.setRentalCharge(0.0);
        rentalProduct.setExtendedRentPeriod(0);
        rentalProduct.setIsLocked(Boolean.FALSE);
        rentalProduct.setLockReason("");
        rentalProduct.setDepreciation(
                soldProduct.getDepreciation());
        Double netPrice = OrderServiceUtil.calculateNetPrice(
                soldProduct.getSellPrice(),
                soldProduct.getDiscount());
        LOGGER.info("SoldProduct :: {}", soldProduct);
        Double returnAmount = OrderServiceUtil.calculateDeposite(
                netPrice, soldProduct.getDepreciation(), INITIAL_RETURN_PERIOD);
        returnAmount = returnAmount < 0 ? 0 : returnAmount;
        if (returnAmount < 1) {
            rentalProduct.setIsEligibleForReturn(false);
        } else {
            rentalProduct.setIsEligibleForReturn(true);
        }
        rentalProduct.setRentalCharge(netPrice - returnAmount);
        rentalProduct.setRefundAmount(returnAmount);
        rentalProduct.setDeposite(returnAmount);
        return rentalProduct;
    }

    public void setCustomerOrderStatus(final PaymentMode PAYMENT_MODE,
            CustomerOrder customerOrder) {
        if (PAYMENT_MODE == PaymentMode.CASH) {
            customerOrder.setOrderStatus(OrderStatus.RECEIVED);
        } else {
            customerOrder.setOrderStatus(OrderStatus.CREATED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public OnlinePaymentOrderDetails getPaymentOrderNumber(
            CustomerOrder customerOrder) {
        LOGGER.info("Setting payment details for customer : {}",
                customerOrder.getCustomerId());
        OnlinePaymentOrderDetails onlinePaymentOrderDetails
                = new OnlinePaymentOrderDetails();
        try {
            int amountInPaisa = (int) (customerOrder.getFinalAmount() * 100);
            JSONObject orderRequest = createOrderRequest(amountInPaisa,
                    customerOrder);
            Order order = razorpayClient.Orders.create(orderRequest);
            LOGGER.info("Payment order is created : {} for customer : {}",
                    order,
                    customerOrder.getCustomerId());
            String paymentOrderNumber = order.toJson().get("id").toString();
            setOnlinePaymentOrderDetails(onlinePaymentOrderDetails,
                    amountInPaisa, customerOrder, paymentOrderNumber);
        } catch (RazorpayException ex) {
            LOGGER.error(ex.getMessage());
        }
        return onlinePaymentOrderDetails;
    }

    @Transactional(rollbackFor = Exception.class)
    public JSONObject createOrderRequest(int amountInPaisa,
            CustomerOrder customerOrder) throws JSONException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaisa);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", customerOrder.getOrderNumber());
        orderRequest.put("payment_capture", false);
        return orderRequest;
    }

    @Transactional(rollbackFor = Exception.class)
    public void setOnlinePaymentOrderDetails(
            OnlinePaymentOrderDetails onlinePaymentOrderDetails,
            int amountInPaisa, CustomerOrder customerOrder,
            String paymentOrderNumber) {
        onlinePaymentOrderDetails.setAmount(amountInPaisa);
        onlinePaymentOrderDetails.setCurrency("INR");
        onlinePaymentOrderDetails.setCustomerEmailId(
                customerOrder.getCustomer().getEmailId());
        StringBuilder customerFullName = new StringBuilder();
        customerFullName.append(customerOrder.getCustomer().getFirstName()).
                append(" ").
                append(customerOrder.getCustomer().getLastName());
        onlinePaymentOrderDetails.setCustomerName(customerFullName.toString());
        onlinePaymentOrderDetails.setKey(razorpayServiceConfig.keyId);
        onlinePaymentOrderDetails.setOrderNumberForPayment(paymentOrderNumber);
        onlinePaymentOrderDetails.setCustomerContact(
                customerOrder.getCustomer().getContact());
    }

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrder deductQuantityForOrder(CustomerOrder customerOrder,
            Map<String, List<SoldProduct>> groupByProduct) {
        LOGGER.info("Deducting product for customer : {}",
                customerOrder.getCustomerId());
        customerOrder.getProductsList().clear();
        List<SoldProduct> updatedList = new ArrayList<>();
        Map<String, Integer> releaseList = new HashMap<>();
        for (Map.Entry<String, List<SoldProduct>> entry
                : groupByProduct.entrySet()) {
            releaseList.put(entry.getKey(), entry.getValue().size());
            List<SoldProduct> soldProductList = entry.getValue();
            List<StockItem> stockItems
                    = productService.deductStock(
                            soldProductList.get(0).getProductId(),
                            soldProductList.size());
            int stockCount = 0;
            for (StockItem stockItem : stockItems) {
                SoldProduct soldProduct = soldProductList.get(stockCount++);
                LOGGER.info("SoldProductID :: {}", soldProduct.getSoldProductId());
                soldProduct.setPurchasedPrice(stockItem.getPurchasedPrice());
                soldProduct.setPurchasedOn(stockItem.getPurchasedOn());
                soldProduct.setPurchasedFrom(stockItem.getVendorId());
                soldProduct.setStockItemId(stockItem.getStockItemId());
            }
            updatedList.addAll(soldProductList);
        }
        customerOrder.setProductsList(updatedList);
        if (!"cash".equals(customerOrder.getPaymentMode())) {
            productService.releaseQuantity(releaseList);
        }
        return customerOrder;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePaymentStatus(String orderNumber, Boolean paymentStatus) {
        LOGGER.info("Updating payment status value : {} for order: {}",
                paymentStatus,
                orderNumber);
        return customerOrderDao.updatePaymentStatus(orderNumber, paymentStatus) != 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        LOGGER.info("Updating order status value : {} for order: {}",
                orderStatus,
                orderNumber);
        if (null != orderStatus) {
            Boolean isUpdated;
            MailDTO mailDTO;
            Map<String, Object> mailBody;
            switch (orderStatus) {
                case DELIVERED:
                    isUpdated = customerOrderDao.updateDeliveryStatus(
                            orderNumber, orderStatus, LocalDate.now()) != 0;
                    mailDTO = getOrderInfoById(orderNumber);
                    mailBody
                            = prepareDeliveryConfirmationMail(mailDTO);
                    mailService.sendDeliveryConfirmationMail(mailBody);
                    return isUpdated;
                case RECEIVED:
                    isUpdated = customerOrderDao.updateReceiveStatus(
                            orderNumber, orderStatus, LocalDate.now()) != 0;
                    return isUpdated;
                case SHIPPED:
                    isUpdated = customerOrderDao.updateShippingStatus(
                            orderNumber, orderStatus, LocalDate.now()) != 0;
                    mailDTO = getOrderInfoById(orderNumber);
                    mailBody = prepareShippingConfirmationMail(mailDTO);
                    mailService.sendShippingConfirmationMail(mailBody);
                    return isUpdated;
                case CREATED:
                    break;
                case CANCELLED:
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    private Map<String, Object> prepareDeliveryConfirmationMail(MailDTO mailDTO) {
        DeliveryConfirmationMail deliveryConfirmationMail = new DeliveryConfirmationMail();
        deliveryConfirmationMail.setDeliveredOn(mailDTO.getDeliveredOn());
        deliveryConfirmationMail.setFirstName(mailDTO.getFirstName());
        deliveryConfirmationMail.setLastName(mailDTO.getLastName());
        deliveryConfirmationMail.setOrderDate(mailDTO.getOrderDate());
        deliveryConfirmationMail.setOrderNumber(mailDTO.getOrderNumber());
        deliveryConfirmationMail.setPayableAmount(mailDTO.getPayableAmount());
        deliveryConfirmationMail.setPaymentMode(mailDTO.getPaymentMode());
        deliveryConfirmationMail.setProductList(mailDTO.getProductList());
        deliveryConfirmationMail.setShippingCharge(mailDTO.getShippingCharge());
        deliveryConfirmationMail.setShippingAddress(mailDTO.getShippingAddress());
        Map<String, Object> mailBody = new HashMap<>();
        mailBody.put("deliveryInfo", deliveryConfirmationMail);
        MailInfo mailInfo = new MailInfo();
        mailInfo.setToAddressList(Arrays.asList(mailDTO.getEmailAddress()));
        mailInfo.setMailSubject(appProperty.deliveryConfirmationMailSub);
        mailBody.put("mailInfo", mailInfo);
        return mailBody;
    }

    private Map<String, Object> prepareShippingConfirmationMail(MailDTO mailDTO) {
        ShippingConfirmationMail shippingConfirmationMail = new ShippingConfirmationMail();

        shippingConfirmationMail.setShippedOn(mailDTO.getShippedOn());
        shippingConfirmationMail.setFirstName(mailDTO.getFirstName());
        shippingConfirmationMail.setLastName(mailDTO.getLastName());

        shippingConfirmationMail.setOrderDate(mailDTO.getOrderDate());
        shippingConfirmationMail.setOrderNumber(mailDTO.getOrderNumber());
        shippingConfirmationMail.setPayableAmount(mailDTO.getPayableAmount());
        shippingConfirmationMail.setPaymentMode(mailDTO.getPaymentMode());
        shippingConfirmationMail.setShippingAddress(mailDTO.getShippingAddress());
        shippingConfirmationMail.setProductList(mailDTO.getProductList());
        shippingConfirmationMail.setShippingCharge(mailDTO.getShippingCharge());
        Map<String, Object> mailBody = new HashMap<>();
        mailBody.put("shippingInfo", shippingConfirmationMail);
        MailInfo mailInfo = new MailInfo();
        mailInfo.setToAddressList(Arrays.asList(mailDTO.getEmailAddress()));
        mailInfo.setMailSubject(appProperty.shippingConfirmationMailSub);
        mailBody.put("mailInfo", mailInfo);
        return mailBody;
    }

    private Map<String, Object> prepareOrderConfirmationMail(MailDTO mailDTO) {
        OrderConfirmationMail orderConfirmationMail = new OrderConfirmationMail();
        orderConfirmationMail.setFirstName(mailDTO.getFirstName());
        orderConfirmationMail.setLastName(mailDTO.getLastName());
        orderConfirmationMail.setOrderDate(mailDTO.getOrderDate());
        orderConfirmationMail.setOrderNumber(mailDTO.getOrderNumber());
        orderConfirmationMail.setPayableAmount(mailDTO.getPayableAmount());
        orderConfirmationMail.setPaymentMode(mailDTO.getPaymentMode());
        orderConfirmationMail.setProductList(mailDTO.getProductList());
        orderConfirmationMail.setOrderedOn(mailDTO.getOrderDate());
        orderConfirmationMail.setShippingCharge(mailDTO.getShippingCharge());
        Map<String, Object> mailBody = new HashMap<>();
        mailBody.put("orderInfo", orderConfirmationMail);
        MailInfo mailInfo = new MailInfo();
        mailInfo.setToAddressList(Arrays.asList(mailDTO.getEmailAddress()));
        mailInfo.setMailSubject(appProperty.orderConfirmationMailSub);
        mailBody.put("mailInfo", mailInfo);
        return mailBody;
    }

    @Transactional(rollbackFor = Exception.class)
    public MailDTO getOrderInfoById(String orderNumber) {
        MailDTO mailDTO = mailInfoRepository.getOrderInfoById(orderNumber);
        String shippingAddress = customerOrderDao.
                findShippingAddressByOrderNumber(orderNumber);
        if (mailDTO != null) {
            Customer customer = userService.getCustomerDetails(
                    mailDTO.getCustomerId());
            mailDTO.setShippingAddress(shippingAddress);
            mailDTO.setEmailAddress(customer.getEmailId());
            mailDTO.setFirstName(customer.getFirstName());
            mailDTO.setLastName(customer.getLastName());

        }
        return mailDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteDraftByCustomerId(String customerId) {
        return productService.deleteDraftByCustomer(customerId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void processOnlinePaidOrder(String paymentOrderNumber) {
        LOGGER.info("Processing for online payment");
        CustomerOrder customerOrder = customerOrderDao.
                findByPaymentOrderNumber(paymentOrderNumber);
        if (customerOrder == null) {
            throw new ResourceNotFoundException("Customer Order",
                    paymentOrderNumber,
                    "Payment Order Number is not exist");
        }
        updateOrderStatus(customerOrder.getOrderNumber(), OrderStatus.RECEIVED);
        String customerId = customerOrder.getCustomerId();
        deleteDraftByCustomerId(customerId);
        LOGGER.info("Order :: {}", customerOrder);
        Map<String, List<SoldProduct>> groupByProduct = groupByProduct(
                customerOrder.getProductsList());
        customerOrder = deductQuantityForOrder(customerOrder, groupByProduct);
        customerOrderDao.save(customerOrder);
        customerOrder.setCustomer(userService.getCustomerDetails(customerId));
        updateOrderStatus(customerOrder.getOrderNumber(), OrderStatus.RECEIVED);
        String customerMailAddress = userService.getCustomerEmailAddress(
                customerId);
        MailDTO mailDTO = getOrderInfo(
                customerMailAddress, customerOrder, groupByProduct);
        Map<String, Object> mailBody = prepareOrderConfirmationMail(mailDTO);
        mailService.sendMailForOrderConfirmation(mailBody);
    }

    public ServiceResponse listCustomerOrders(CustomerOrderPayload orderPayload) {
        if (orderPayload.customerId == null) {
            throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Customer Id is require",
                    "List Customers");
        }
        List<CustomerOrder> customerOrders = orderRepo.listCustomerOrders(orderPayload);
        if (customerOrders.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            return new ServiceResponse(HttpStatus.OK.value(), customerOrders);
        }
    }

    public ServiceResponse listCustomerOrders(
            CustomerOrderForAdminPayload orderPayload) {
        List<CustomerOrder> customerOrders = orderRepo.
                listCustomerOrders(orderPayload);
        if (customerOrders.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            return new ServiceResponse(HttpStatus.OK.value(), customerOrders);
        }
    }
}
