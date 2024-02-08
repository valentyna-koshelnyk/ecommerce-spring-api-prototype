package com.startsteps.ecommerceapi.service.dto;

import org.springframework.data.domain.Page;

public interface IEntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    Page<D> toDtoPage(Page<E> entitiesPage);

}