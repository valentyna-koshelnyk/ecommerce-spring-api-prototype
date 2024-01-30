package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name="cart_product")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart shoppingCart;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "total_cost")
    private double totalCost;

    public CartProduct(Product product, ShoppingCart shoppingCart, Long quantity) {
        this.product = product;
        this.shoppingCart = shoppingCart;
        this.quantity = quantity;
    }

    public CartProduct(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
