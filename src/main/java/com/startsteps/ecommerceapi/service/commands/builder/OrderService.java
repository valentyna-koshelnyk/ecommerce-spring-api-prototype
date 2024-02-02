package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.Orders;

public interface OrderService {
    void saveOrder(Orders order);

    void placeOrder(Long shoppingCartId);
}
