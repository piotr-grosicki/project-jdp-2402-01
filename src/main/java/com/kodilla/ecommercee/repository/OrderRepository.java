package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default void delete(Order entity) {
        entity.setActive(false);
        save(entity);
    }

    List<Order> findAllByActiveTrue();

    Optional<Order> findByIdAndActiveTrue(Long orderId);
}
