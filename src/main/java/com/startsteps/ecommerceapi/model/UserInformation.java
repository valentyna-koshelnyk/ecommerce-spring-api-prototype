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
    @Column(unique = false, name = "First_Name")
    private String firstName;
    @Column(unique = false, name = "Last_Name")
    private String lastName;
    @Column(unique = false, name = "Address")
    private String address;
    @Column(unique = true, name = "Phone")
    private String phone;


}
