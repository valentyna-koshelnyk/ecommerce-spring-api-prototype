package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.exceptions.CartIsEmptyException;
import com.startsteps.ecommerceapi.exceptions.OrderNotFoundException;
import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.model.UserInformation;
import com.startsteps.ecommerceapi.persistence.OrderRepository;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.persistence.UserRepository;
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
    private final UserRepository userRepository;
    private final OrderValidatorImpl orderValidator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderProcessor orderProcessor, OrderBuilder orderBuilder, ShoppingCartRepository shoppingCartRepository, CartServiceImpl cartService, UserRepository userRepository, OrderValidatorImpl orderValidator) {
        this.orderRepository = orderRepository;
        this.orderProcessor = orderProcessor;
        this.orderBuilder = orderBuilder;
        this.cartService = cartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
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
                .orderService(this)
                .shoppingCart(shoppingCart)
                .shoppingCartRepository(shoppingCartRepository)
                .userRepository(userRepository)
                .user(shoppingCart.getUser());
        OrderCommand placeOrderCommand = new PlaceOrderCommand(builder);
        orderProcessor.processOrder(placeOrderCommand);
    }

    public String printOrder(Long shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(()-> new CartIsEmptyException("Cart not found"));
        return orderRepository.findOrdersByShoppingCart(shoppingCart)
                .toString();
    }

    public void cancelOrder(Long orderId){ // might be better to keep the order but change status to CANCELED
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        orderRepository.delete(orders);
    }

}