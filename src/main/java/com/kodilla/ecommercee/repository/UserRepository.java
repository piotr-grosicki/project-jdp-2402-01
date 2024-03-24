package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default void delete(User entity) {
        entity.setActive(false);
        save(entity);
    }
    Optional<User> findByIdAndActiveTrue(Long id);
}
