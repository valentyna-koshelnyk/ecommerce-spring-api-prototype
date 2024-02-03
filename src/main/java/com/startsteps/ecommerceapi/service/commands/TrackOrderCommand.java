package com.startsteps.ecommerceapi.service.commands;

import com.startsteps.ecommerceapi.model.Orders;

public class TrackOrderCommand implements OrderCommand{
    private final Orders orders;

    public TrackOrderCommand(Orders orders) {
        this.orders = orders;
    }

    @Override
    public void execute() {

    }

}
