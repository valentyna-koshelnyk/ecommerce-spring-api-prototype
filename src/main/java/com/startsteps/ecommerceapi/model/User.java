package com.startsteps.ecommerceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startsteps.ecommerceapi.utils.Default;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @NonNull
   @Column(name = "UserID")
   private Long userId;

   @NonNull
   @Column(name = "Username", unique = true)
   @JsonIgnore
   private String username;

   @NonNull
   @Column(name = "Email", unique = true)
   @JsonIgnore
   private String email;

   @NonNull
   @Column(name = "Password")
   @JsonIgnore
   private String password;

   @NonNull
   @Column(name = "User_Role")
   @JsonIgnore
   @Enumerated(EnumType.STRING)
   private UserRoles userRoles;

   @CreationTimestamp
   @Column(name = "Registration_Date", nullable = false, updatable = false)
   @JsonIgnore
   private LocalDateTime registrationDate;

   @UpdateTimestamp
   @Column(name = "Update_Date")
   @JsonIgnore
   private LocalDateTime updateDate;

   @NonNull             //TODO: user confirmed email
   @Column(name = "Approved")
   @JsonIgnore
   private Boolean approved;

   @NonNull
   @Column(name = "Pending")
   @JsonIgnore
   private Boolean pending;

   @OneToOne
   @JoinColumn(name = "cart_id")
   private ShoppingCart shoppingCart;

   @OneToMany(mappedBy = "user")
   private List<Orders> orders;
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_info_id", referencedColumnName = "id")
   private UserInformation userInformation;
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
}
