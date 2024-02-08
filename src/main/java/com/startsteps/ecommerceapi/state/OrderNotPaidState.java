package com.startsteps.ecommerceapi.state;

import com.startsteps.ecommerceapi.model.OrderStatus;
import com.startsteps.ecommerceapi.model.Orders;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderNotPaidState implements OrderState{
    @Override
    public void next(Orders order) {
        order.setOrderStatus(OrderStatus.PAID);
        order.updateState();

    }

    @Override
    public void prev(Orders order) {
        order.setOrderStatus(OrderStatus.CANCELED);
    }

    @Override
    public void printStatus(Orders orders) {
        log.info("Waiting for payment");
    }
}
