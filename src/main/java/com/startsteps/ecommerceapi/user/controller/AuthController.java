package com.startsteps.ecommerceapi.user.controller;

import com.startsteps.ecommerceapi.user.exceptions.UserAlreadyExistsException;
import com.startsteps.ecommerceapi.user.model.User;
import com.startsteps.ecommerceapi.user.payload.request.SignupRequest;
import com.startsteps.ecommerceapi.user.service.UserServiceImpl;
import com.startsteps.ecommerceapi.user.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserServiceImpl userService;
    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            User registeredUser = userService.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully! UserID: " + registeredUser.getUserId()));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}
