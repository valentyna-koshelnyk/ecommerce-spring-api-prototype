package com.startsteps.ecommerceapi.model;

import com.startsteps.ecommerceapi.state.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "UserInfoID")
    private UserInformation userInformation;
    @OneToOne
    private ShoppingCart shoppingCart;
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private List<OrderProducts> orderItems = new ArrayList<>();;
    @Transient
    private OrderState state = new OrderNotPaidState();


    public Orders(UserInformation userInformation, @NonNull ShoppingCart shoppingCart) {
        this.userInformation = userInformation;
        this.shoppingCart = shoppingCart;
        this.orderStatus = OrderStatus.NOT_PAID;
        this.state = new OrderNotPaidState();
    }

    public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void updateState() {
        switch (this.orderStatus) {
            case NOT_PAID:
                this.state = new OrderNotPaidState();
                break;
            case PAID:
                this.state = new OrderPaidState();
                break;
            case IN_PROCESS:
                this.state = new OrderInProcessState();
                break;
            case SHIPPED:
                this.state = new OrderShippedState();
                break;
            case DELIVERED:
                this.state = new OrderDelieveredState();
                break;
        }
    }
    @Transactional
    public void nextState() {
        state.next(this);
    }

    @Transactional
    public void previousState() {
        state.prev(this);
    }
    public void printState(){
        state.printStatus(this);
    }

    @PostLoad
    private void initializeState() {
        updateState();
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
