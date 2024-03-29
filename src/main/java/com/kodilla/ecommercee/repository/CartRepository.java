package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    default void delete(Cart entity) {
        entity.setActive(false);
        save(entity);
    }

    Optional<Cart> findByIdAndActiveTrue(Long cartId);
}