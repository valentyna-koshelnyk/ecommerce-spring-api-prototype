package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.service.UserServiceImpl;
import com.startsteps.ecommerceapi.service.commands.PlaceOrderCommand;
import com.startsteps.ecommerceapi.service.commands.builder.OrderServiceImpl;
import com.startsteps.ecommerceapi.service.commands.builder.ProvideUserInfoBuilderImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/orders")
public class OrderController {
    private final UserServiceImpl userService;

    public OrderController(UserServiceImpl userService, PlaceOrderCommand placeOrder, OrderServiceImpl orderService, ProvideUserInfoBuilderImpl provideUserInfoBuilder) {
        this.userService = userService;
        this.placeOrder = placeOrder;
        this.orderService = orderService;
        this.provideUserInfoBuilder = provideUserInfoBuilder;
    }

    private final PlaceOrderCommand placeOrder;
    private final OrderServiceImpl orderService;

    private final ProvideUserInfoBuilderImpl provideUserInfoBuilder;

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
    @PostMapping("/place/{shoppingCartId}") //TODO: handle exception if order already exists
    public ResponseEntity<?> placeOrder(@PathVariable Long shoppingCartId) {
        orderService.placeOrder(shoppingCartId);
        return ResponseEntity.ok(new MessageResponse(orderService.printOrder(shoppingCartId)));
    }
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new MessageResponse("Order " + orderId + " has been cancelled"));
    }



}
