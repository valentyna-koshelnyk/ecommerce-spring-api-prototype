package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.OrderStatus;
import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findOrdersByShoppingCart(ShoppingCart shoppingCartId);
    void delete(Orders entity);
    Orders findUserByOrderId(Long userId);
    Orders findOrdersByOrderId(Long orderId);
    Orders findOrdersByOrderStatusAndOrderId(OrderStatus orderStatus, Long orderId);
    Optional<Orders> findTopByUserOrderByOrderIdDesc(User user);
    List<Orders> findOrdersByShoppingCartOrderByOrderCreatedAtDesc(ShoppingCart shoppingCart);


}