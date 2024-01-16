package com.startsteps.ecommerceapi.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PrivilegeID")
    private Long id;
    @Column(name = "Privilege_name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "role_privileges", // name of the join table
            joinColumns = @JoinColumn(name = "PrivilegeID"),
            inverseJoinColumns = @JoinColumn(name = "RoleID")
    )
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }
}
