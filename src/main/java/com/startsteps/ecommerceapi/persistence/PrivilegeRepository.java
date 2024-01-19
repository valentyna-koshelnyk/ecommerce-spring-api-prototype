package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);

}
