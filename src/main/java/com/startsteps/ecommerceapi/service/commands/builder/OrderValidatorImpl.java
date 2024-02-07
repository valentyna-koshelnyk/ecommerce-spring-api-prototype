package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.exceptions.CartIsNotFound;
import com.startsteps.ecommerceapi.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderValidatorImpl {
    public boolean validateOrder(Orders order) {
        if (order == null)  {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if(order.getShoppingCart().getProducts() == null){
            throw new CartIsNotFound("Shopping cart cannot be null");
        } if (order.getUserInformation() == null) {
            throw new IllegalArgumentException("User information cannot be null");
        }
        return true;
    }
}
