package com.startsteps.ecommerceapi.user.service;

import com.startsteps.ecommerceapi.user.model.PasswordResetToken;
import com.startsteps.ecommerceapi.user.model.User;
import com.startsteps.ecommerceapi.user.service.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    User registerUser (UserDTO user);
    User registerAdmin(UserDTO user);
    User resetPassword(String email);

    void createPasswordResetTokenForUser(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);
}
