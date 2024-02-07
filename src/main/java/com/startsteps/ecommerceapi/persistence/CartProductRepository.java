package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.CartProduct;
import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


 public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
        Page<CartProduct> findAllByShoppingCart(ShoppingCart shoppingCart, Pageable pageable);
        CartProduct findCartProductByProduct(Product product);
        Optional<CartProduct> findCartProductByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
        List<CartProduct> findProductsByShoppingCart(ShoppingCart shoppingCart);
        List<CartProduct> findCartProductByShoppingCart(ShoppingCart shoppingCart);

        void deleteByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);



 }