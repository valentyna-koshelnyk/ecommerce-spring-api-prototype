package com.startsteps.ecommerceapi.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleID")
    private Long roleId;
    @Enumerated
    @Column(length = 20)
    private UserRoles roleName;
    String description;
    String permission;
    Long userId;
}
