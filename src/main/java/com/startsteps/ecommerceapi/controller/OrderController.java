package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.model.OrderProducts;
import com.startsteps.ecommerceapi.model.Orders;
import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.service.CartServiceImpl;
import com.startsteps.ecommerceapi.service.UserServiceImpl;
import com.startsteps.ecommerceapi.service.commands.PlaceOrderCommand;
import com.startsteps.ecommerceapi.service.commands.builder.OrderServiceImpl;
import com.startsteps.ecommerceapi.service.commands.builder.ProvideUserInfoBuilderImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/orders")
public class OrderController {
    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;

    public OrderController(UserServiceImpl userService, CartServiceImpl cartService, PlaceOrderCommand placeOrder, OrderServiceImpl orderService, ProvideUserInfoBuilderImpl provideUserInfoBuilder) {
        this.userService = userService;
        this.cartService = cartService;
        this.placeOrder = placeOrder;
        this.orderService = orderService;
        this.provideUserInfoBuilder = provideUserInfoBuilder;
    }

    private final PlaceOrderCommand placeOrder;
    private final OrderServiceImpl orderService;

    private final ProvideUserInfoBuilderImpl provideUserInfoBuilder;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
    @PostMapping("/place/{userId}") //TODO: handle exception if order already exists
    public ResponseEntity<MessageResponse> placeOrder(@PathVariable Long userId) {
        Long shoppingCartId = cartService.findShoppingCartByUser(userId).getCartId();
        orderService.placeOrder(shoppingCartId);
        return ResponseEntity.ok(new MessageResponse(orderService.printOrder(shoppingCartId)));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
    @DeleteMapping("/cancel/{userId}/{orderId}")
    public ResponseEntity<MessageResponse> cancelOrder(@PathVariable Long userId,
                                                       @PathVariable Long orderId ) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new MessageResponse("Order " + orderId + " has been cancelled"));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
    @GetMapping("/view/old/{userId}")
    public ResponseEntity<MessageResponse> getOrderHistory(@PathVariable Long userId){
        Long shoppingCartId = cartService.findShoppingCartByUser(userId).getCartId();
        List<OrderProducts> orderProductsList = orderService.getOrderHistory(shoppingCartId);
        return ResponseEntity.ok(new MessageResponse(orderProductsList.toString()));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/status/{orderId}/next")
    public ResponseEntity<MessageResponse> moveOrderStatusNext(@PathVariable Long orderId){
        Orders updatedOrder = orderService.moveToNextState(orderId);
        return ResponseEntity.ok(new MessageResponse("Order is " + updatedOrder.getOrderStatus()));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/status/{orderId}/revert")
    public ResponseEntity<MessageResponse> revertOrderStatus(@PathVariable Long orderId){
        Orders revertedOrder = orderService.previousState(orderId);
        return ResponseEntity.ok(new MessageResponse("Order is " + revertedOrder.getOrderStatus()));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #userId == principal.id")
    @GetMapping("/check-status/{userId}/{orderId}")
    public ResponseEntity<MessageResponse> checkOrderStatus(@PathVariable Long userId,
                                                            @PathVariable Long orderId){
            orderService.printOrder(orderId);
            return ResponseEntity.ok((new MessageResponse("")));

    }


}
