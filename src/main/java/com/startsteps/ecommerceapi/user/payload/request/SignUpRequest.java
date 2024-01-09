package com.startsteps.ecommerceapi.user.payload.request;

import com.startsteps.ecommerceapi.user.validation.PasswordMatching;
import com.startsteps.ecommerceapi.user.validation.ValidEmail;
import com.startsteps.ecommerceapi.user.validation.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PasswordMatching(message = "Passwords do not match")
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    String userId;
    @NotNull (message = "Username cannot be null")
    @NotEmpty (message = "Username cannot be empty")
    String userName;

    @ValidEmail(message = "Email is not valid")
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    String email;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @ValidPassword
    String password;

    @NotNull(message = "Matching password cannot be null")
    @NotEmpty(message = "Matching password cannot be empty")
    String matchingPassword;


}
