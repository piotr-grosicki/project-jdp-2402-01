package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    default void delete(Group entity) {
        entity.setActive(false);
        save(entity);
    }

    List<Group> findAllByActiveTrue();

    Optional<Group> findByIdAndActiveTrue(Long id);
}
