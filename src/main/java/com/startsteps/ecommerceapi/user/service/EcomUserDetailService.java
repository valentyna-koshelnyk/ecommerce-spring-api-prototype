package com.startsteps.ecommerceapi.user.service;

import com.startsteps.ecommerceapi.user.repository.UserRepository;
import com.startsteps.ecommerceapi.user.exceptions.UserNotFoundException;
import com.startsteps.ecommerceapi.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EcomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found " + username));
        return new EcomUserAdapter(user);
    }
}
