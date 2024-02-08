package com.startsteps.ecommerceapi.service.dto;

import com.startsteps.ecommerceapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends IEntityMapper<UserDTO, User> {
    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    @Override
    default Page<UserDTO> toDtoPage(Page<User> entitiesPage) {
        List<UserDTO> dtoList = entitiesPage.getContent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, entitiesPage.getPageable(), entitiesPage.getTotalElements());
    }

}
