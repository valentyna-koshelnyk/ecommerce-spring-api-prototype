package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.payload.request.SignupRequest;
import com.startsteps.ecommerceapi.model.PasswordResetToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    User registerUser (SignupRequest user);
    User registerAdmin(SignupRequest user);
    User resetPasswordSendEmail(String userEmail);
    void createPasswordResetTokenForUser(User user, String token);
    boolean changePassword(PasswordResetToken token, String newPassword);
    Optional<PasswordResetToken> getPasswordResetToken(PasswordResetToken token);
}
