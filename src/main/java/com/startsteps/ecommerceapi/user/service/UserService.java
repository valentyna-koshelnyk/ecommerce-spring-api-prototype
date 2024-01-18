package com.startsteps.ecommerceapi.user.service;

import com.startsteps.ecommerceapi.user.model.PasswordResetToken;
import com.startsteps.ecommerceapi.user.model.User;
import com.startsteps.ecommerceapi.user.payload.request.SignupRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
