package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findUserByUserId(Long userId);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
