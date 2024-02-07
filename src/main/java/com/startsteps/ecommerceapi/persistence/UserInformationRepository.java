package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {
   Optional<UserInformation> findUserInformationByUser(User user);
}
