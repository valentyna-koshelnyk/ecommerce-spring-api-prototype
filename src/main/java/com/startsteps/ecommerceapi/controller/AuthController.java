package com.startsteps.ecommerceapi.controller;

import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.payload.request.LoginRequest;
import com.startsteps.ecommerceapi.payload.request.SignupRequest;
import com.startsteps.ecommerceapi.payload.response.MessageResponse;
import com.startsteps.ecommerceapi.payload.response.UserInfoResponse;
import com.startsteps.ecommerceapi.security.jwt.JwtUtil;
import com.startsteps.ecommerceapi.service.UserDetailsImpl;
import com.startsteps.ecommerceapi.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Authentication controller", description = "Through this API user or admin can register and signin. Admin has the only authorization right to register another admin ")

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
    @Operation(summary= "Simple registration for users", description= "User sends registration" +
            " request with unique username, email, password and matching password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Validation failed for argument")})
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
            User registeredUser = userService.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully! UserID: " + registeredUser.getUserId()));
    }
    @Operation(summary= "Admin adds a new admin to the system", description= "Allows admins to register new administrators" +
            " (request with unique username, email, password and matching password)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Validation failed for argument")})
    @PostMapping("/registerAdmin")
    public ResponseEntity<MessageResponse> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
            User registeredAdmin = userService.registerAdmin(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("Admin registered successfully! UserID: " + registeredAdmin.getUserId()));
    }
    @Operation(summary= "User authentication endpoint", description= "User signs in, JWT token generated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Bad credentials")})
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
                        "You successfully signed in"));
    }
    @Operation(summary= "Returns information on the current signed in user", description= "Check which username you're currently using")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Bad credentials")})
    @GetMapping("/checkUser")
    public String checkUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }
}
