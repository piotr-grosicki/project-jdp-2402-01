package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default void delete(User entity) {
        entity.setActive(false);
        save(entity);
    }

    List<User> findAllByActiveTrue();

    Optional<User> findByIdAndActiveTrue(Long id);
}
