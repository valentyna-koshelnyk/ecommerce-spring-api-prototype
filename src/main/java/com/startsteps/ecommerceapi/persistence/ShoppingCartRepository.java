package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
