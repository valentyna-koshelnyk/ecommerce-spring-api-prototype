package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.OrderStatus;
import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findOrdersByShoppingCart(ShoppingCart shoppingCartId);
    void delete(Orders entity);
    Orders findUserByOrderId(Long userId);
    Orders findOrdersByOrderId(Long orderId);
    Orders findOrdersByOrderStatusAndOrderId(OrderStatus orderStatus, Long orderId);
    List<Orders> findOrdersByShoppingCartOrderByOrderCreatedAtDesc(ShoppingCart shoppingCart);


}