package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Override
    List<Order> findAll();

    @Override
    Order save(Order order);

    @Override
    Optional<Order> findById(Long readerId);

    @Override
    void deleteById(Long orderId);

}
