package com.startsteps.ecommerceapi.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    private String username;
    private String email;
    private String password;
    private USER_ROLES UserRole;
}
