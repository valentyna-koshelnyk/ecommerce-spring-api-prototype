package com.startsteps.ecommerceapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "TotalPrice")
    private Long totalprice;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "User_information")
    @Embedded
    private UserInformation userInformation;

}
