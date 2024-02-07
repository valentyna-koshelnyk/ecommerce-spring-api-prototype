package com.startsteps.ecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Cart is not found")
public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart is not found");
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}
