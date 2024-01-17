package com.startsteps.ecommerceapi.payload.request;

import com.startsteps.ecommerceapi.validation.ValidEmail;
import com.startsteps.ecommerceapi.validation.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @ValidEmail(message = "Email is not valid")
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @ValidPassword
    private String password;

    @NotNull(message = "Matching password cannot be null")
    @NotEmpty(message = "Matching password cannot be empty")
    private String matchingPassword;

}
  

