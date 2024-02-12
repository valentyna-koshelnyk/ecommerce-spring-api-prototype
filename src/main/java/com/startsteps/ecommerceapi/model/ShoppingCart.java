package com.startsteps.ecommerceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Long cartId;
    // One simple cart for one user, or a complex app multiple carts for one user (wishlist, favourite items etc)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NonNull
    private User user;
    @OneToMany(mappedBy = "shoppingCart",
            cascade = CascadeType.ALL)
    private List<CartProduct> products;
    @CreationTimestamp
    @Column(name = "Created_Date", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime cartCreatedAt;

    @Column(name = "total_price")
    private Double priceTotal;

    @Override
   public String toString(){
        return "priceTotal " + priceTotal;
    }

}
