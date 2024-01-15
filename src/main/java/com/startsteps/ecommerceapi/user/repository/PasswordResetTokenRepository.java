package com.startsteps.ecommerceapi.user.repository;

import com.startsteps.ecommerceapi.user.model.PasswordResetToken;
import com.startsteps.ecommerceapi.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(PasswordResetToken token);
    Optional<PasswordResetToken> findByUser(User user);
    void delete(PasswordResetToken entity);
}