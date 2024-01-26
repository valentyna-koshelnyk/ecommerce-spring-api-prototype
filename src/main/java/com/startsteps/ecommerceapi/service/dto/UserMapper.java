package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends IEntityMapper<UserDTO, User> {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);

}
