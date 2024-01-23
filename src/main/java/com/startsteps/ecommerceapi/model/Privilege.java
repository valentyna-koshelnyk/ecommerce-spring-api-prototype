package com.startsteps.ecommerceapi.model;

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
    public Privilege(String name) {
        this.name = name;
    }
}
