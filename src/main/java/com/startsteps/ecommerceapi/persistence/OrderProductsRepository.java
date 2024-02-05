package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.OrderProducts;
import com.startsteps.ecommerceapi.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {
    void save(Orders order);
    OrderProducts findByOrders(Orders orders);
}
