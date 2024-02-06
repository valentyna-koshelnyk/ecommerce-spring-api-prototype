package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProducts, Long> {
}
