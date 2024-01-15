package com.startsteps.ecommerceapi.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
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
   @Enumerated
   private UserRoles UserRole;

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

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "roles",
           joinColumns = @JoinColumn(name = "userID"),
           inverseJoinColumns = @JoinColumn(name = "roleID"))
   private Set<Role> roleSet;

   public User(@NonNull Long userId, @NonNull String username, @NonNull String email, @NonNull String password, @NonNull Boolean approved, @NonNull Boolean pending) {
      this.userId = userId;
      this.username = username;
      this.email = email;
      this.password = password;
      this.approved = approved;
      this.pending = pending;
   }
}
