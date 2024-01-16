package com.startsteps.ecommerceapi.user.service.dto;

import com.startsteps.ecommerceapi.user.model.Role;
import com.startsteps.ecommerceapi.user.validation.PasswordMatching;
import com.startsteps.ecommerceapi.user.validation.ValidEmail;
import com.startsteps.ecommerceapi.user.validation.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PasswordMatching(message = "Passwords do not match")
public class UserDTO {

    private Long userId;

    @NotNull (message = "Username cannot be null")
    @NotEmpty (message = "Username cannot be empty")
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

    @NotNull
    private Set<Role> role;


    public UserDTO(Long userId, String username, String email, String password, String matchingPassword) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}
