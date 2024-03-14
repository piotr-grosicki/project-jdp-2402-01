package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@NonNullApi
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    List<Order> findAll();

    @Override
    Order save(Order order);

    @Override
    Optional<Order> findById(Long readerId);

    @Override
    void deleteById(Long orderId);
}
