package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.service.CartServiceImpl;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart")
public class CartController {

    private final CartServiceImpl cartService;

    @Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/addProduct/{id}")
    public ResponseEntity<?> addProductToShoppingCart(@PathVariable Long id,
                                                      @RequestBody ProductDTO product,
                                                      @RequestBody int quantity) {

       // cartService.addProductToCartproduct, quantity);
        return ResponseEntity.ok("Product added to the shopping cart");
    }
    }
