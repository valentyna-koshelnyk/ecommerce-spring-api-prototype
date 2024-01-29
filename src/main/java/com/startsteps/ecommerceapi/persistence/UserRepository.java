package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.ShoppingCart;
import com.startsteps.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    ShoppingCart findShoppingCartByUserId(long userId);

}
