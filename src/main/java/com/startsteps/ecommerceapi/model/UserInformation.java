package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInformation {
    // TODO: add custom validations for all properties
    @Column(nullable = false, unique = false, name = "First_Name")
    private String firstName;
    @Column(nullable = false, unique = false, name = "Last_Name")
    private String lastName;
    @Column(nullable = false, unique = false, name = "Address")
    private String address;
    @Column(nullable = false, unique = true, name = "Phone")
    private String phone;


}
