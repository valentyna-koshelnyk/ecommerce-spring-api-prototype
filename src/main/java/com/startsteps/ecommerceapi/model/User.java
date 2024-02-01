package com.startsteps.ecommerceapi.model;

import com.startsteps.ecommerceapi.utils.Default;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@Component
public class User{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @NonNull
   @Column(name = "UserID")
   private Long userId;

   @NonNull
   @Column(name = "Username", unique = true)
   private String username;

   @NonNull
   @Column(name = "Email", unique = true)
   private String email;

   @NonNull
   @Column(name = "Password")
   private String password;

   @NonNull
   @Column(name = "User_Role")
   @Enumerated(EnumType.STRING)
   private UserRoles userRoles;

   @CreationTimestamp
   @Column(name = "Registration_Date", nullable = false, updatable = false)
   private LocalDateTime registrationDate;

   @UpdateTimestamp
   @Column(name = "Update_Date")
   private LocalDateTime updateDate;

   @NonNull             //TODO: user confirmed email
   @Column(name = "Approved")
   private Boolean approved;

   @NonNull
   @Column(name = "Pending")
   private Boolean pending;

   @OneToOne
   @JoinColumn(name = "cart_id")
   private ShoppingCart shoppingCart;
   public User(@NonNull String username, @NonNull String email, @NonNull String password, @NonNull Boolean approved, Boolean pending) {
      this.username = username;
      this.email = email;
      this.password = password;
      this.approved = approved;
      this.pending = pending;
   }
@Default
public User(@NonNull String username, @NonNull String email, @NonNull String password) {
      this.username = username;
      this.email = email;
      this.password = password;
   }
   public User(@NonNull Long userId) {
      this.userId = userId;
   }
}
