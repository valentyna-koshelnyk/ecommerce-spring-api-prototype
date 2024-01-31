package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.payload.request.ProductAddRequest;

public interface CartService {
    void addProductToCart(ProductAddRequest productAddRequest);

    void reduceProductStock(Long quantity, Long productId);

    void addCartProduct(Product product, ShoppingCart cart, Long quantity);

    double calculateProductCost(Product product, Long quantity);

    double updateTotalCost(CartProduct cartProduct,
                           Product product,
                           Long quantity);

    boolean isProductInUserCart(Long productId, Long userId);
}
