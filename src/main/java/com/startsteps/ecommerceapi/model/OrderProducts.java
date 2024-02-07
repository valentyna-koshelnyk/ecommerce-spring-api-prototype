package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_id")
    private Long productId;
    @Column (name = "quantity")
    private long quantity;
    @Column(name = "priceProduct")
    private Double priceProduct;
    @Column (name = "shoppingCartId")
    private Long shoppingCartId;
    @Column(name = "orderCreatedAt")
    private LocalDateTime orderCreatedAt;
    @Column(name = "orderId")
    private long orderId;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private List<CartProduct> cartProduct = new ArrayList<>();;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}

