package com.startsteps.ecommerceapi.service.commands;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.service.commands.builder.OrderServiceImpl;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaceOrderCommand implements OrderCommand{

    private final OrderServiceImpl orderService;
    private  Orders order;

    @Setter
    private ShoppingCart shoppingCart;
    @Autowired
    public PlaceOrderCommand(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }


    @Override
    public void execute() {
        if (shoppingCart == null) {
            throw new IllegalStateException("Cart must be set before executing the command.");
        }

        orderService.placeOrder(shoppingCart.getCartId());
    }

    @Override
    public void unexecute() {
        orderService.cancelOrder(order);

    }
}
