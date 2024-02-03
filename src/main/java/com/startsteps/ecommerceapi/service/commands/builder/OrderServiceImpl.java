package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.model.UserInformation;
import com.startsteps.ecommerceapi.persistence.OrderRepository;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import com.startsteps.ecommerceapi.service.commands.OrderCommand;
import com.startsteps.ecommerceapi.service.commands.OrderProcessor;
import com.startsteps.ecommerceapi.service.commands.PlaceOrderCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProcessor orderProcessor;
    private final OrderBuilder orderBuilder;
    private final CartServiceImpl cartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderValidatorImpl orderValidator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderProcessor orderProcessor, OrderBuilder orderBuilder, ShoppingCartRepository shoppingCartRepository, CartServiceImpl cartService, OrderValidatorImpl orderValidator) {
        this.orderRepository = orderRepository;
        this.orderProcessor = orderProcessor;
        this.orderBuilder = orderBuilder;
        this.cartService = cartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderValidator = orderValidator;
    }

    @Override
    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }
    public void placeOrder(Long shoppingCartId) {
        ShoppingCart shoppingCart = cartService.findShoppingCartByCartId(shoppingCartId);
        if (shoppingCart == null) {
            throw new IllegalStateException("No cart found with id: " + shoppingCartId);
        }
        UserInformation userInformation = shoppingCart.getUser().getUserInformation();
        log.debug("Building order with ShoppingCart id: {}", shoppingCartId);
        OrderBuilder builder = new OrderBuilder()
                .shoppingCart(shoppingCart)
                .information(userInformation)
                .orderValidator(orderValidator)
                .orderService(this);
        OrderCommand placeOrderCommand = new PlaceOrderCommand(builder);
        orderProcessor.processOrder(placeOrderCommand);
    }

    public void cancelOrder(Orders orders){
        orderRepository.delete(orders);
    }
}