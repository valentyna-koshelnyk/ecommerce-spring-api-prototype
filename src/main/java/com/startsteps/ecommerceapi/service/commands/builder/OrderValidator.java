package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.Orders;

public interface OrderValidator {
    boolean validateOrder(Orders order);
}
