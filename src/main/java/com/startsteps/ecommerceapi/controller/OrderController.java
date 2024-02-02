package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.persistence.UserRepository;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import com.startsteps.ecommerceapi.service.UserServiceImpl;
import com.startsteps.ecommerceapi.service.commands.PlaceOrderCommand;
import com.startsteps.ecommerceapi.service.commands.builder.OrderBuilder;
import com.startsteps.ecommerceapi.service.commands.builder.OrderServiceImpl;
import com.startsteps.ecommerceapi.service.commands.builder.OrderValidatorImpl;
import com.startsteps.ecommerceapi.service.commands.builder.ProvideUserInfoBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/orders")
public class OrderController {
    private final UserServiceImpl userService;
    private final PlaceOrderCommand placeOrder;
    private final OrderServiceImpl orderService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderValidatorImpl orderValidator;
    private final CartServiceImpl cartService;
    private final ProvideUserInfoBuilderImpl provideUserInfoBuilder;

    @Autowired
    public OrderController(OrderServiceImpl orderService, UserServiceImpl userService, PlaceOrderCommand placeOrder, ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, OrderValidatorImpl orderValidator, CartServiceImpl cartService, ProvideUserInfoBuilderImpl provideUserInfoBuilder) {
        this.placeOrder = placeOrder;
        this.orderService = orderService;
        this.userService = userService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.orderValidator = orderValidator;
        this.cartService = cartService;
        this.provideUserInfoBuilder = provideUserInfoBuilder;
    }

    @PostMapping("/{userId}/updateInformation")
    public ResponseEntity<MessageResponse> addUserInfo(@PathVariable Long userId,
                                        @RequestBody UserInformation userInformation) {
        User user = userService.findUserByUserId(userId);
        UserInformation userInfoBuilder = provideUserInfoBuilder
                .user(user)
                .firstName(userInformation.getFirstName())
                .lastName(userInformation.getLastName())
                .phone(userInformation.getPhone())
                .address(userInformation.getAddress())
                .build();
        return ResponseEntity.ok(new MessageResponse("Information was updated"));
    }
    @RequestMapping(value = "/place/{cartId}")
    public ResponseEntity<MessageResponse> placeOrder(@PathVariable("cartId") Long cartId, @RequestBody UserInformation userInformation) {
        ShoppingCart shoppingCart = cartService.findShoppingCartByCartId(cartId);
        User user = shoppingCart.getUser();
        Orders orders = new OrderBuilder()
                .orderValidator(orderValidator)
                .shoppingCart(shoppingCart)
                .orderService(orderService)
                .information(user.getUserInformation())
                .user(user)
                .shoppingCartRepository(shoppingCartRepository)
                .userRepository(userRepository)
                .build();
        return ResponseEntity.ok(new MessageResponse(orders.toString()));
    }
}
