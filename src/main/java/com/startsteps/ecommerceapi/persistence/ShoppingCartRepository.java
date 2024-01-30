package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.user.userId = :userId")
    ShoppingCart findShoppingCartByUserId(@Param("userId") Long userId);
    ShoppingCart findShoppingCartByCartId(Long shoppingCartId);


}
