package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBuilder {
    private OrderValidator orderValidator;
    private OrderService orderService;
    private ShoppingCart shoppingCart;
    private UserInformation userInformation;
    private User user;

    public OrderBuilder orderValidator(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
        return this;
    }

    public OrderBuilder orderService(OrderService orderService) {
        this.orderService = orderService;
        return this;
    }
    public OrderBuilder information(UserInformation userInformation) {
        this.userInformation = userInformation;
        return this;
    }

    public Orders build(){
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setUserInformation(userInformation);
        orders.setShoppingCart(shoppingCart);
        orders.setTotalprice(orders.getTotalprice());
        if (!orderValidator.validateOrder(orders)) {
            throw new IllegalArgumentException("Invalid order");
        }
        orderService.saveOrder(orders);
        return orders;

    }

}
