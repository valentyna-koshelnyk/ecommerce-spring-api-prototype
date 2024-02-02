package com.startsteps.ecommerceapi.service.commands.builder;

import com.startsteps.ecommerceapi.model.UserInformation;

public interface IOrder {
    UserInformation getFirstName();
    UserInformation getLastName();
    UserInformation getAddress();
    UserInformation getPhone();
}
