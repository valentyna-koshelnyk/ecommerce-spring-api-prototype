package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.payload.request.ProductAddRequest;
import com.startsteps.ecommerceapi.service.dto.CartProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    void addProductToCart(ProductAddRequest productAddRequest);

    void reduceProductStock(Long quantity, Long productId);

    void increaseStock(Long quantity, Long productId);

    void addNewProductToCart(Product product, ShoppingCart cart, Long quantity);

    ShoppingCart findShoppingCartByCartId(Long cartId);

    double calculateProductCost(Product product, Long quantity);

    double updateTotalCost(ShoppingCart shoppingCart);

    boolean isProductInUserCart(Product product, ShoppingCart shoppingCart);

    Page<CartProductDTO> getProductsInCart(Long cartId, Pageable pageable);
}
