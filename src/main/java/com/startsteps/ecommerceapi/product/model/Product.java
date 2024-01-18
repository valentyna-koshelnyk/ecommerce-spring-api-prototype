package com.startsteps.ecommerceapi.product.model;

import jakarta.persistence.*;
import lombok.*;

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
}
