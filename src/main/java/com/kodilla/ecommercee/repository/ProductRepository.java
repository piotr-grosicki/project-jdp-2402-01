package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default void delete(Product entity) {
        entity.setActive(false);
        save(entity);
    }

    Optional<Product> findByIdAndActiveTrue(Long id);

    List<Product> findAllByActiveTrue();
}