package com.startsteps.ecommerceapi.user.repository;

import com.startsteps.ecommerceapi.user.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);

}
