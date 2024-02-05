package com.startsteps.ecommerceapi.service.commands;

import com.startsteps.ecommerceapi.service.commands.builder.OrderServiceImpl;

public class CancelOrderCommand implements OrderCommand{
    private final OrderServiceImpl orderService;

    public CancelOrderCommand(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Override
    public void execute() {

    }
}
