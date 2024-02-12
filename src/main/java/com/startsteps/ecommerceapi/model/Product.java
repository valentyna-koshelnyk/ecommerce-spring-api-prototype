package com.startsteps.ecommerceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startsteps.ecommerceapi.utils.Default;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name="Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Product_ID")
    private Long productId;
    @NonNull
    @Column(name= "Product_Name", length = 255)
    private String productName;
    @NonNull
    @Column(name = "Price")
    @DecimalMin("0.01")
    private Double price;
    @Column(name = "Description", length = 255)
    private String description;
    @NonNull
    @Column(name = "Stock")
    @JsonIgnore
    private Long stock;
    public void setAddedAtDate(LocalDateTime addedAtDate) {
        this.addedAtDate = LocalDateTime.now();
    }
    @DateTimeFormat
    @Column(name = "Add_Date", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime addedAtDate;
    @NonNull
    @Column(name = "Product_Category", length = 255)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Default
    public Product(String productName, Double price, String description, Long stock, ProductCategory category) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }

    @Override
    public String toString(){
        return " \n oProduct Name: " + this.getProductName() +
                "\n Product Price: " + this.getPrice() +
                "\n Product Description: " + this.getDescription() +
                "\n Product Category: " + this.getCategory();
                }
    }

