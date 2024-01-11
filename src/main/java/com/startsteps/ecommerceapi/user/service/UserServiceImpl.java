package com.startsteps.ecommerceapi.user.service;

import com.startsteps.ecommerceapi.user.exceptions.UserAlreadyExistsException;
import com.startsteps.ecommerceapi.user.model.User;
import com.startsteps.ecommerceapi.user.repository.UserRepository;
import com.startsteps.ecommerceapi.user.service.dto.UserDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * service layer class for serving "api/users" endpoints
 */

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override //TODO: authorized method
    public Optional<User> findByEmail(String emailAddress) {
        return Optional.empty();
    }

    @Override // TODO: authorized mehtod
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
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
        // set role
        return userRepository.save(newUser);
    }
    @Override //TODO: authorized method
    public User registerAdmin(UserDTO user) {
        return null;
    }

    @Override
    public User resetPassword(UserDTO user) {return null};// TODO: create reset password token/ implement methods/
    // add it for respective endpoint {
    }




