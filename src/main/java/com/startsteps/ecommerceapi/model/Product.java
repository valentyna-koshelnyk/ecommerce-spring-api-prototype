package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
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
    private double price;
    @Column(name = "Description", length = 255)
    private String description;
    @NonNull
    @Column(name = "Stock")
    private long stock;
    @CreationTimestamp
    @Column(name = "Add_Date", nullable = false, updatable = false)
    LocalDateTime addedAtDate;
    @NonNull
    @Column(name = "Product_Category", length = 255)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    @OneToMany(mappedBy = "product")
    private List<CartProduct> cartProducts;

    public Product(String productName, double price, String description, long stock,  ProductCategory category) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }
    @Override
    public String toString(){
        if(this.getStock() < 10 && this.getStock() > 2) {
            System.out.println("Product is almost out of stock. Hurry up to order! ");
        } else if (this.getStock() <= 2 && this.getStock() > 0) {
            System.out.println("Last item");
        }
        return "Product Name: " + this.getProductName() +
                " Product Price: " + this.getPrice() +
                " Product Description: " + this.getDescription() +
                " Product Category: " + this.getCategory();
                }
    }

