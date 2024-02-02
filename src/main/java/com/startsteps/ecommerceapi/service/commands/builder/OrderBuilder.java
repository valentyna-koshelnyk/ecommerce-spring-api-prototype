package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.UserInformation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBuilder {
    private OrderValidator orderValidator;
    private OrderService orderService;
    private UserInformation userInformation;

    public OrderBuilder orderValidator(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
        return this;
    }

    public OrderBuilder orderService(OrderService orderService) {
        this.orderService = orderService;
        return this;
    }
    public OrderBuilder payment(UserInformation userInformation) {
        this.userInformation = userInformation;
        return this;
    }

    public Orders build(){
        Orders orders = new Orders();
        orders.setOrderService(orderService);

    }

}
