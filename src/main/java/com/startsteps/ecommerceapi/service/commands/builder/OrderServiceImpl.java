package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;
import com.startsteps.ecommerceapi.persistence.OrderRepository;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartServiceImpl cartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderValidatorImpl orderValidator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, CartServiceImpl cartService, OrderValidatorImpl orderValidator) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderValidator = orderValidator;
    }

    @Override
    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }
    @Override
    public void placeOrder(Long shoppingCartId) {
        User user = cartService.findShoppingCartByCartId(shoppingCartId).getUser();
        UserInformation userInformation = user.getUserInformation();
        OrderBuilder orderBuilder = new OrderBuilder()
                .orderValidator(orderValidator)
                .information(userInformation)
                .orderService(this);

        Orders orders = orderBuilder.build();
    }

    public void cancelOrder(Orders orders){
        orderRepository.delete(orders);
    }
}