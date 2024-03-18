package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default void delete(Product entity) {
        entity.setActive(false);
        save(entity);
    }
    List<Product> findAllByActiveTrue();
    Optional<Product> findByIdAndActiveTrue(Long id);
}
