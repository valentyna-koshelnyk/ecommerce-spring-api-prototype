package com.startsteps.ecommerceapi.user.repository;

import com.startsteps.ecommerceapi.user.model.Role;
import com.startsteps.ecommerceapi.user.payload.request.SignupRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
