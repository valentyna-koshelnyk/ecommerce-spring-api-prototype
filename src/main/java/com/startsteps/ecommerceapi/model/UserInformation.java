package com.startsteps.ecommerceapi.model;

import com.startsteps.ecommerceapi.validation.ValidPhone;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user_information")
public class UserInformation {
    // TODO: validation for properties:firstName and lastName is risky for validation, address require embedded class

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(unique = false, nullable = true, name = "First_Name")
    private String firstName;
    @Column(unique = false, name = "Last_Name", nullable = true)
    private String lastName;
    @Column(unique = false, name = "Address", nullable = true)
    private String address;
    @Column(unique = true, name = "Phone", nullable = true)
    @ValidPhone
    private String phone;
    @OneToOne(mappedBy = "userInformation")
    private User user;
}
