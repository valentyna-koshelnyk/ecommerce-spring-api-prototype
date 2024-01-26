package com.startsteps.ecommerceapi.service.dto;


import com.startsteps.ecommerceapi.model.ShoppingCart;

public record CartItem(ShoppingCart shoppingCart, ProductDTO product) {}


