package com.startsteps.ecommerceapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    private String message;
    private String jwt;

    public MessageResponse(String message) {
        this.message = message;
    }
}
