package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.exceptions.UserNotFoundException;
import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;
import com.startsteps.ecommerceapi.persistence.OrderRepository;
import com.startsteps.ecommerceapi.persistence.ShoppingCartRepository;
import com.startsteps.ecommerceapi.persistence.UserInformationRepository;
import com.startsteps.ecommerceapi.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ProvideUserInfoBuilderImpl implements ProvideUserInfoBuilder {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private User user;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;

    @Autowired
    public ProvideUserInfoBuilderImpl(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, UserInformationRepository userInformationRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
    }

    @Override
    public ProvideUserInfoBuilderImpl firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public ProvideUserInfoBuilderImpl lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public ProvideUserInfoBuilderImpl address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public ProvideUserInfoBuilderImpl phone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public ProvideUserInfoBuilder user(User user) {
        this.user=user;
        return this;
    }


    @Override
    public UserInformation build() {
        User userBuilder = userRepository.findUserByUserId(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User was not found"));
        UserInformation userInformation = userInformationRepository.findUserInformationByUser(userBuilder)
                .orElse(new UserInformation());

        userInformation.setUser(userBuilder);
        userInformation.setFirstName(firstName);
        userInformation.setLastName(lastName);
        userInformation.setAddress(address);
        userInformation.setPhone(phone);

        userInformationRepository.save(userInformation);
        user.setUserInformation(userInformation);
        userRepository.save(user);
        return userInformation;
    }



}
