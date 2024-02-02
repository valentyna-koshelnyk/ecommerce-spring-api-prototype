package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Product;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.user.userId = :userId")
    ShoppingCart findShoppingCartByUserId(@Param("userId") Long userId);

    @Query("SELECT sc FROM ShoppingCart sc JOIN FETCH sc.products WHERE sc.cartId = :cartId")
    Optional<ShoppingCart> findByIdWithProducts(@Param("cartId") Long cartId);
    Optional<List<Product>> findProductsByCartId(Long cartId);
    ShoppingCart findShoppingCartByCartId(Long cartId);
}