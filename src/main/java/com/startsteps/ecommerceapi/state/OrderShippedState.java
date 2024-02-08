package com.startsteps.ecommerceapi.state;

import com.startsteps.ecommerceapi.model.OrderStatus;
import com.startsteps.ecommerceapi.model.Orders;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderShippedState implements OrderState{
    @Override
    public void next(Orders order) {
        order.setOrderStatus(OrderStatus.DELIVERED);

    }

    @Override
    public void prev(Orders order) {
        order.setOrderStatus(OrderStatus.IN_PROCESS);

    }

    @Override
    public void printStatus(Orders order) {
        log.info("Order was shipped");
    }
}
