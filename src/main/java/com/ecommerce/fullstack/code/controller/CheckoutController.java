package com.ecommerce.fullstack.code.controller;

import com.ecommerce.fullstack.code.dto.Purchase;
import com.ecommerce.fullstack.code.dto.PurchaseResponse;
import com.ecommerce.fullstack.code.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;
    @PostMapping("/purchase")
    public PurchaseResponse processOrder(@RequestBody Purchase purchase) {
        return checkoutService.processOrder(purchase);
    }

}
