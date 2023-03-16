package com.ecommerce.fullstack.code.service;

import com.ecommerce.fullstack.code.dto.Purchase;
import com.ecommerce.fullstack.code.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse processOrder(Purchase purchase);

}
