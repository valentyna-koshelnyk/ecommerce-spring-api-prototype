package com.startsteps.ecommerceapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String message;
    private String jwt;

    public JwtResponse(String message) {
        this.message = message;
    }
}
