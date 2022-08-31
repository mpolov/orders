package com.polov.shop.orders.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.polov.shop.orders.model.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    
    Optional<Order> findById(Long id);

}
