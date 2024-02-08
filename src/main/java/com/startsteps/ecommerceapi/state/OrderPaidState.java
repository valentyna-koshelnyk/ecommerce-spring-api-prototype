package com.startsteps.ecommerceapi.state;

import com.startsteps.ecommerceapi.model.OrderStatus;
import com.startsteps.ecommerceapi.model.Orders;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderPaidState implements OrderState{
    @Override
    public void next(Orders order) {
        order.setStatus(OrderStatus.IN_PROCESS);
        order.updateState();
    }

    @Override
    public void prev(Orders order) {
        order.setOrderStatus(OrderStatus.NOT_PAID);

    }

    @Override
    public void printStatus(Orders orders) {
        log.info("Order has been paid. Not shipped yet.");

    }
}
