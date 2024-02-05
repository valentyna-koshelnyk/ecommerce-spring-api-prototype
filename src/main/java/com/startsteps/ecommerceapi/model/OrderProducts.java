package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders_products")
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_ProductID")
    private Long orderProductsId;
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
    @OneToMany(mappedBy = "orderProducts")
    private List<CartProduct> cartProduct;
    @OneToOne
    @JoinColumn(name = "OrderID")
    private Orders orders;

}
