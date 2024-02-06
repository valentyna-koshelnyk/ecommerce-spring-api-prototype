package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Orders findOrdersByShoppingCart(ShoppingCart shoppingCartId);
    void delete(Orders entity);
    User findUserByOrderId(Long orderId);

}