package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ShoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Long cartId;
    // One simple cart for one user, or a complex app multiple carts for one user (wishlist, favourite items etc)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NonNull
    private User user;
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;
    @Column(name = "Quantity")
    private Long quantity;
    @CreationTimestamp
    @Column(name = "Created_Date", nullable = false, updatable = false)
    private LocalDateTime cartCreatedAt;

}
