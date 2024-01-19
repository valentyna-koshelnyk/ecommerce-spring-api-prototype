package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(PasswordResetToken token);
    Optional<PasswordResetToken> findByUser(User user);
    void delete(PasswordResetToken entity);
}
