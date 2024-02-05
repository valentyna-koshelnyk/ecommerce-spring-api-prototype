package com.startsteps.ecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Cart is not found")
public class CartIsNotFound extends RuntimeException {
    public CartIsNotFound() {
        super("Cart is not found");
    }

    public CartIsNotFound(String message) {
        super(message);
    }
}
