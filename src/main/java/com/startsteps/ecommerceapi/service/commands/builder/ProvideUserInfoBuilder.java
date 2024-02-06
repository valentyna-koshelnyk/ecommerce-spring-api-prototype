package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.User;
import com.startsteps.ecommerceapi.model.UserInformation;

public interface ProvideUserInfoBuilder {
    ProvideUserInfoBuilder firstName(String firstName);
    ProvideUserInfoBuilder lastName(String lastName);
    ProvideUserInfoBuilder address(String address);
    ProvideUserInfoBuilder phone(String phone);

    ProvideUserInfoBuilder user(User user);

    UserInformation build();
}
