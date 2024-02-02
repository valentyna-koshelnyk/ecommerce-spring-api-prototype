package com.startsteps.ecommerceapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserInformation {
    private Long infoId;
    @Column(nullable = false, unique = false)
    private String firstName;
    @Column(nullable = false, unique = false)
    private String lastName;
    @Column(nullable = false, unique = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String phone;

}
