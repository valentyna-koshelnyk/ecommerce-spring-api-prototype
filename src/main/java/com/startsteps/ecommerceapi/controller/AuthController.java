package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.payload.request.LoginRequest;
import com.startsteps.ecommerceapi.payload.request.SignupRequest;
import com.startsteps.ecommerceapi.payload.response.UserInfoResponse;
import com.startsteps.ecommerceapi.security.jwt.JwtUtil;
import com.startsteps.ecommerceapi.service.UserDetailsImpl;
import com.startsteps.ecommerceapi.service.UserDetailsServiceImpl;
import com.startsteps.ecommerceapi.service.UserServiceImpl;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserServiceImpl userService;
    @Autowired
    private UserDetailsService userDetailsService;
    private UserDetails userDetails;
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
            User registeredUser = userService.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully! UserID: " + registeredUser.getUserId()));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
            User registeredAdmin = userService.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("Admin registered successfully! UserID: " + registeredAdmin.getUserId()));
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);
        final String jwt = jwtUtil.generateTokenFromUsername(userDetails.getUsername());
        System.out.println("JWT Token: " + jwt);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @GetMapping("/checkUser")
    public String checkUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }
}
