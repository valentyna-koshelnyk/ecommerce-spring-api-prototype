package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Table(name = "order_items")
@Entity
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderProductId;
    @Column(name = "product")
    private String productName;
    @Column (name = "quantity")
    private long quantity;
    @Column(name = "priceProduct")
    private Double priceProduct;
    @Column (name = "shoppingCartId")
    private Long shoppingCartId;
    @Column(name = "orderCreatedAt")
    private LocalDateTime orderCreatedAt;
    @ElementCollection
    private List<CartProduct> cartProduct;
}

