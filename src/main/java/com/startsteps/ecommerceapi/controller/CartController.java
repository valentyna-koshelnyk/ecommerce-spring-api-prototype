package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.payload.request.ProductAddRequest;
import com.startsteps.ecommerceapi.payload.response.ApiResponse;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/cart")
public class CartController {

    private final CartServiceImpl cartService;

    @Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/addProduct/")
    public ResponseEntity<ApiResponse> addProductToShoppingCart(@RequestBody ProductAddRequest request) {
        cartService.addProductToCart(request);
        ApiResponse response = new ApiResponse("Product added to the shopping cart");
        return ResponseEntity.ok(response);
    }
    }
