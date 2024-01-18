package com.startsteps.ecommerceapi.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Product_ID")
    private Long productId;
    @NonNull
    @Column(name= "Product_Name")
    private String productName;
    @NonNull
    @Column(name = "Price")
    private double price;
    @Column(name = "Description")
    private String description;
    @NonNull
    @Column(name = "Stock")
    private long stock;
    @CreationTimestamp
    @Column(name = "Add_Date", nullable = false, updatable = false)
    LocalDateTime addedAtDate;

    //TODO: to add product category
}
