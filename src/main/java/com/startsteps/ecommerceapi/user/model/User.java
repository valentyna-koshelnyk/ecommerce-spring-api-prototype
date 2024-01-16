package com.startsteps.ecommerceapi.user.model;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel
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
   LocalDateTime registrationDate;

   @UpdateTimestamp
   @Column(name = "Update_Date")
   LocalDateTime updateDate;

   @NonNull             //TODO: user confirmed email
   @Column(name = "Approved")
   Boolean approved;

   @NonNull
   @Column(name = "Pending")
   Boolean pending;
   
   public User(@NonNull Long userId, @NonNull String username, @NonNull String email, @NonNull String password,  @NonNull Boolean approved, @NonNull Boolean pending) {
      this.userId = userId;
      this.username = username;
      this.email = email;
      this.password = password;
      this.approved = approved;
      this.pending = pending;
   }

   public User(@NonNull String username, @NonNull String email, @NonNull String password) {
      this.username = username;
      this.email = email;
      this.password = password;
   }

}
