package com.ecommerce.fullstack.code.service;

import com.ecommerce.fullstack.code.dto.Purchase;
import com.ecommerce.fullstack.code.dto.PurchaseResponse;
import com.ecommerce.fullstack.code.entity.Customer;
import com.ecommerce.fullstack.code.entity.Order;
import com.ecommerce.fullstack.code.entity.OrderItem;
import com.ecommerce.fullstack.code.entity.Product;
import com.ecommerce.fullstack.code.exception.CustomApiException;
import com.ecommerce.fullstack.code.repository.OrderRepository;
import com.ecommerce.fullstack.code.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private OrderRepository orderRepository;
    private Set<OrderItem> orderItems;
    private Order order;
    @Autowired
    private ProductRepository productRepository;

    // logger
    Logger log = LoggerFactory.getLogger(getClass());

    // custom exception with loggs
    @Override
    @Transactional
    public PurchaseResponse processOrder(Purchase purchase) {
        log.info("v Info: " + purchase);
        int productsInStock = productRepository.findAll().size();
        order = purchase.getOrder();
        if (order == null) {
            log.error("Given order Can't be Null");
            throw new CustomApiException("Given order Can't be Null");
        }
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        orderItems = purchase.getOrderItems();
        if (orderItems.size() < 1) {
            log.error("Minimum Order Items must be >= 1");
            throw new CustomApiException("Minimum Order Items must be >= 1");
        } else if (orderItems.size() > productsInStock) {
            log.error("Sorry, Available Order Items must be <="
                    + productsInStock);
            throw new CustomApiException("Sorry, Available Order Items must be <="
                    + productsInStock);
        }
        orderItems.forEach(this::addOrderItems);
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());
        order.setOrderItems(orderItems);
        Customer customer = purchase.getCustomer();
        if (customer == null) {
            log.error("Customer Can't be Null");
            throw new CustomApiException("Customer Can't be Null");
        } else {
            order.setCustomer(customer);
        }
        orderRepository.save(order);
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    public void addOrderItems(OrderItem item) {
        if (item != null) {
            if (orderItems == null) {
                orderItems = new HashSet<>();
            }
            Optional<Product> product = productRepository.findById(item.getProductId());
            if (!product.isPresent()) {
                log.error("Sorry, Item with id: "
                        + item.getProductId() + " out of stock now");
                throw new CustomApiException("Sorry, Item with id: "
                        + item.getProductId() + " out of stock now");
            }
            orderItems.add(item);
            item.setOrder(order);
        }
    }

}
