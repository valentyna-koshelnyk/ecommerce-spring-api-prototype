package com.startsteps.ecommerceapi.service.dto;

public interface IEntityMapper<D, E> {

    D toDto(E e);

    E toEntity(D d);

//    List<D> toDto(List<E> eList);
//
//    List<E> toEntity(List<D> dList);

}
