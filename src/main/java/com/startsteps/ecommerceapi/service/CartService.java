package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.service.dto.CartItem;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.service.dto.ProductDTO;

public interface CartService {
    void addProductToCart(Long userId, Long productId, int quantity);

    void reduceProductStock(int quantity, long productId);

    void addCartProduct(ProductDTO product, ShoppingCart cart, int quantity);

    CartItem createCartItem(Long userId, Long productId, int quantity);

    boolean isProductInUserCart(Long productId, Long userId);
}
