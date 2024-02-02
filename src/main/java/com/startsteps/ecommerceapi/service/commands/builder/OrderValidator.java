package com.startsteps.ecommerceapi.service.commands.builder;

public interface OrderValidator {
    boolean validateOrder(IOrder order);
}
