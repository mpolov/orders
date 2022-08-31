package com.polov.shop.orders.repository;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.polov.shop.orders.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
	Optional<Product> findById(Long id);

}
