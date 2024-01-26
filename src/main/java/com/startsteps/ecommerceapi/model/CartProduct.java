package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart shoppingCart;

    @Column(name = "quantity")
    @Min(1)
    private long quantity;

    public CartProduct(Product product, ShoppingCart shoppingCart, long quantity) {
        this.product = product;
        this.shoppingCart = shoppingCart;
        this.quantity = quantity;
    }
}
