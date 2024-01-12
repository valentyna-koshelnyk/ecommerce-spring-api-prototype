package com.startsteps.ecommerceapi.user.service;

import com.startsteps.ecommerceapi.user.dao.PasswordResetTokenRepository;
import com.startsteps.ecommerceapi.user.exceptions.UserAlreadyExistsException;
import com.startsteps.ecommerceapi.user.exceptions.UserNotFoundException;
import com.startsteps.ecommerceapi.user.model.PasswordResetToken;
import com.startsteps.ecommerceapi.user.model.User;
import com.startsteps.ecommerceapi.user.dao.UserRepository;
import com.startsteps.ecommerceapi.user.service.dto.UserDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

/**
 * service layer class for serving "api/users" endpoints
 */

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
   @Autowired
   public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public User registerUser(UserDTO user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException("This email " + user.getEmail() + " already exists");
        }
        else if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException("This username " + user.getUsername() + " is already used");
        }
        User newUser = new User((user.getUserId()), user.getUsername(), passwordEncoder.encode(user.getPassword()),
                user.getEmail(),false, true);
        return userRepository.save(newUser);
    }
    @Override //TODO: authorized method
    public User registerAdmin(UserDTO user) {
        return null;
    }

    @Override
    public User resetPassword(String userEmail) {
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
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    private String generateResetToken() {
        String token = UUID.randomUUID().toString();
        return token;
    }
}






