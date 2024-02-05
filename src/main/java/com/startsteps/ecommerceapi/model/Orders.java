package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Long orderId;
    @Column(name = "order_date")
    private LocalDateTime orderCreatedAt;
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
    @Column(name = "Total_Price")
    private Double totalprice;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Embedded
    private UserInformation userInformation;
    @OneToOne
    private ShoppingCart shoppingCart;
    @OneToOne
    private OrderProducts orderProducts;


    public Orders(@NonNull UserInformation userInformation, @NonNull ShoppingCart shoppingCart) {
        this.userInformation = userInformation;
        this.shoppingCart = shoppingCart;
    }
    @Override
    public String toString(){
        return "Order ID: " + orderId +
                ", Order Created At: " + orderCreatedAt +
                ", User: " + user.getUsername() +
                ", Total Price: " + totalprice +
                ", Order Status: " + orderStatus +
                ", User Information: " + userInformation;
    }

}
