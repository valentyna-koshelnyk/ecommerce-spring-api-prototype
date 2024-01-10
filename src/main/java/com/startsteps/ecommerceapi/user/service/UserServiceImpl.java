package com.startsteps.ecommerceapi.user.service;

import com.startsteps.ecommerceapi.user.model.User;
import com.startsteps.ecommerceapi.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * service layer class for serving "api/users" endpoints
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override

    public String encodePassword(String password)


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
