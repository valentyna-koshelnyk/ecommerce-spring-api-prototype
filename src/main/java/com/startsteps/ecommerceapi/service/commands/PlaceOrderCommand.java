package com.startsteps.ecommerceapi.service.commands;

import com.startsteps.ecommerceapi.model.Orders;

public class PlaceOrderCommand implements OrderCommand{
    private final Orders orders;

    public PlaceOrderCommand(Orders orders) {
        this.orders = orders;
    }

    @Override
    public void execute() {

    }
}
