package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.payload.request.ProductAddRequest;
import com.startsteps.ecommerceapi.service.dto.CartProductDTO;

import java.util.List;

public interface CartService {
    void addProductToCart(ProductAddRequest request, Long userId);
    void getTotalCost(Long shoppingCartId);

    void reduceProductStock(Long quantity, Long productId);

    void increaseStock(Long quantity, Long productId);

    void addNewProductToCart(Product product, ShoppingCart cart, Long quantity);

    ShoppingCart findShoppingCartByCartId(Long cartId);

    ShoppingCart findShoppingCartByUser(Long userId);

    double calculateProductCost(Product product, Long quantity);
    boolean isProductInUserCart(Product product, ShoppingCart shoppingCart);

    List<CartProductDTO> getProductsInCart(Long cartId);

    void removeProductFromCart(Long cartId, Long productId);

    void emptyCart(Long cartId);
}
