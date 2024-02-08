package com.startsteps.ecommerceapi.user.controller;

import com.startsteps.ecommerceapi.user.exceptions.UserAlreadyExistsException;
import com.startsteps.ecommerceapi.user.model.User;
import com.startsteps.ecommerceapi.user.payload.request.LoginRequest;
import com.startsteps.ecommerceapi.user.payload.request.SignupRequest;
import com.startsteps.ecommerceapi.user.security.jwt.JwtUtil;
import com.startsteps.ecommerceapi.user.service.EcomUserAdapter;
import com.startsteps.ecommerceapi.user.service.EcomUserDetailService;
import com.startsteps.ecommerceapi.user.service.UserServiceImpl;
import com.startsteps.ecommerceapi.user.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserServiceImpl userService;
    @Autowired
    private EcomUserDetailService userDetailsService;
    @Autowired
    private EcomUserAdapter userDetails;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;
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
            return ResponseEntity.badRequest().body(new MessageResponse("User already exists"));
        }
    }
    @PostMapping("/registerAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            User registeredAdmin = userService.registerAdmin(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("Admin registered successfully! UserID: " + registeredAdmin.getUserId()));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Admin already exists"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtil.generateTokenFromUsername(userDetails.getUsername());
        return ResponseEntity.ok(new MessageResponse("User logged in successfully"));
    }

    @GetMapping("/checkUser")
    public String checkUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }
}
