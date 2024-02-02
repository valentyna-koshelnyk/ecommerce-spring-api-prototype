package com.startsteps.ecommerceapi.service;

import com.startsteps.ecommerceapi.model.*;
import com.startsteps.ecommerceapi.payload.request.SignupRequest;
import com.startsteps.ecommerceapi.persistence.CartProductRepository;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.persistence.UserRepository;
import com.startsteps.ecommerceapi.persistence.PasswordResetTokenRepository;
import com.startsteps.ecommerceapi.exceptions.UserAlreadyExistsException;
import com.startsteps.ecommerceapi.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * service layer class for serving "api/auth/" endpoints
 */

// TODO: implement endpoints for all the mentioned methods (excepts ones used for functional decomposition purposes)
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final CartProductRepository cartProductRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private  final EmailService emailService;
    final int PASS_THRESHOLD = 30;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, PasswordResetTokenRepository passwordResetTokenRepository, CartProductRepository cartProductRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.cartProductRepository = cartProductRepository;
        this.emailService = emailService;
    }

    @Override
    public Optional<User> findUserByEmail(String emailAddress) {
        return userRepository.findByEmail(emailAddress);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User registerUser(SignupRequest user) {
        User newUser = createUser(user);
        newUser.setUserRoles(UserRoles.ROLE_USER);

        ShoppingCart shoppingCart = new ShoppingCart();
        userRepository.save(newUser);
        shoppingCart.setUser(newUser);
        newUser.setShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);

        return newUser;
    }
    @Override
    public User registerAdmin(SignupRequest user) {
        User newUser = createUser(user);
        newUser.setUserRoles(UserRoles.ROLE_ADMIN);
        return userRepository.save(newUser);
    }

    public User createUser(SignupRequest user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException("This email " + user.getEmail() + " already exists");
        }
        else if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException("This username " + user.getUsername() + " is already used");
        }
        User newUser = new User(user.getUsername(),user.getEmail(), passwordEncoder.encode(user.getPassword())
                , false, true);
        newUser.setRegistrationDate(LocalDateTime.now());
        return newUser;
    }

    @Override
    public User resetPasswordSendEmail(String userEmail) {
        if(!userRepository.existsByEmail(userEmail) ) {
            throw new UserNotFoundException("User with the email " + userEmail + " not found");
        }
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new UserNotFoundException("User with the email " + userEmail + " not found"));
        String resetToken = generateResetToken();
        createPasswordResetTokenForUser(user, resetToken);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userEmail);
        email.setSubject("Reset your password");
        email.setText("To reset your password, click the link below:\n" +
                "http://ecommerce-api/reset-password?token=" + resetToken);
        return user;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public boolean changePassword(PasswordResetToken token, String newPassword){
       PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
               .orElseThrow(() -> new NoSuchElementException("Token not found: " + token));
       if(isTokenExpired(token)){
           log.error("Token has been expired. Try again");
           return false;
       }
       User user = passwordResetToken.getUser();
       user.setPassword(passwordEncoder.encode(newPassword));
       userRepository.save(user);
       passwordResetTokenRepository.delete(passwordResetToken);
       return true;
   }

    @Override
    public Optional<PasswordResetToken> getPasswordResetToken(final PasswordResetToken token) {
        return passwordResetTokenRepository.findByToken(token);
    }
    public boolean isTokenExpired(PasswordResetToken token) {
        Instant createdDate = token.getCreatedDate().toInstant();
        Instant now = Instant.now();
        long diff = MINUTES.between(createdDate, now);
        return diff >= PASS_THRESHOLD;

    }

    public User findUserByUserId(Long id){
        return userRepository.findUserByUserId(id)
                .orElseThrow(() -> new UserNotFoundException("User was not found"));
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }
}






