package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.Orders;

public interface OrderService {

    void placeOrder(Long shoppingCartId);
    Orders previousState(Long orderId);
    void checkOrderStatus(Long orderId);
}
