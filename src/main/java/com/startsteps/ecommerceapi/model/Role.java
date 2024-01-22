package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "Role_name",length = 20)
    private UserRoles roleName;
    @Column(name = "Decription")
    String description;
    @Column(name = "Permission")
    String permission;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "RoleID"),
            inverseJoinColumns = @JoinColumn(name = "UserID"))
    Set<User> users;
    @ManyToMany
    @JoinTable(
            name = "Roles_privileges",
            joinColumns = @JoinColumn(
                    name = "RoleId", referencedColumnName = "RoleId"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegeId", referencedColumnName = "privilegeId"))
    private Collection<Privilege> privileges;

    public Role(UserRoles roleName) {
        this.roleName = roleName;
    }
}
