package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    @Column(name = "Price")
    private double price;
    @Column(name = "Description")
    private String description;
    @NotEmpty
    @Column(name = "Stock")
    private long stock;
    @CreationTimestamp
    @Column(name = "Add_Date", nullable = false, updatable = false)
    LocalDateTime addedAtDate;
    @NonNull
    @Column(name = "Product_Category")
    private ProductCategory category;

    public Product(@NonNull String productName, double price, String description, long stock, @NonNull ProductCategory category) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }
}
