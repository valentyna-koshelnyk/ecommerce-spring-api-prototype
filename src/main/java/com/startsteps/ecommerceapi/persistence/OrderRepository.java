package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Orders findOrdersByShoppingCart(ShoppingCart shoppingCartId);
    void delete(Orders entity);
    Orders findUserByOrderId(Long userId);

}