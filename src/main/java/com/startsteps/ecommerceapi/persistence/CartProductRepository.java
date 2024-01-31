package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


 public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
        List<CartProduct> findAllByShoppingCart(ShoppingCart shoppingCart);
        CartProduct findCartProductByProduct(Product product);
        Optional<CartProduct> findCartProductByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
        List<CartProduct> findProductsByShoppingCart(ShoppingCart shoppingCart);
    }