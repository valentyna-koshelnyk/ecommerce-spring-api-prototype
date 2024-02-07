package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProducts, Long> {
    List<OrderProducts> findByShoppingCartId(long shoppingCartId);
}