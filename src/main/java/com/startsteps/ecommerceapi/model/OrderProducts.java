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
    @Column(name = "order_productid")
    private Long orderProductsId;
    @OneToMany
    @JoinColumn(name = "cart_product_id")
    private List<CartProduct> cartProduct;
    @OneToOne
    @JoinColumn(name = "OrderID")
    private Orders orders;

}
