package com.startsteps.ecommerceapi.service.dto;

import java.util.List;

public interface IEntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<D> listToDto(List<E> e);
}