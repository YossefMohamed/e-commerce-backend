package com.ecommerce.fullstack.code.dto;

import com.ecommerce.fullstack.code.entity.Address;
import com.ecommerce.fullstack.code.entity.Customer;
import com.ecommerce.fullstack.code.entity.Order;
import com.ecommerce.fullstack.code.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
