package com.startsteps.ecommerceapi.service.commands;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.service.commands.builder.OrderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaceOrderCommand implements OrderCommand{

    private final OrderBuilder orderBuilder;
    private  Orders order;
    @Autowired
    public PlaceOrderCommand(OrderBuilder orderBuilder) {
        this.orderBuilder = orderBuilder;
    }
    @Override
    public void execute() {
        orderBuilder.build();
    }
}
