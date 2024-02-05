package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends IEntityMapper<UserDTO, User> {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);

}
