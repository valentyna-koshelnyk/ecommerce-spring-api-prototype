package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.*;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.persistence.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
@Service
public class OrderBuilder {
    private OrderValidatorImpl orderValidator;
    private OrderServiceImpl orderService;
    private ShoppingCart shoppingCart;
    private ShoppingCartRepository shoppingCartRepository;
    private UserInformation userInformation;
    private User user;
    private UserRepository userRepository;

    public OrderBuilder orderValidator(OrderValidatorImpl orderValidator) {
        this.orderValidator = orderValidator;
        return this;
    }

    public OrderBuilder shoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
        return this;
    }
    public OrderBuilder orderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
        return this;
    }
    public OrderBuilder information(UserInformation userInformation) {
        this.userInformation = userInformation;
        return this;
    }
    public OrderBuilder user(User user) {
        this.user = user;
        return this;
    }
    public OrderBuilder shoppingCartRepository(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        return this;
    }
    public OrderBuilder userRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    public Orders build(){
        Orders orders = new Orders();
        orders.setShoppingCart(shoppingCart);
        orders.setUser(user);
        orders.setUserInformation(userInformation);
        orders.setTotalprice(shoppingCart.getPriceTotal());
        orders.setOrderStatus(OrderStatus.IN_PROCESS);
        orders.setOrderCreatedAt(LocalDateTime.now());
        if (!orderValidator.validateOrder(orders)) {
            throw new IllegalArgumentException("Invalid order");
        }
        log.info("Order built: {}", orders);

        orderService.saveOrder(orders);
        return orders;
    }

}