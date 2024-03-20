package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByActiveTrue();

    default void delete(Group entity) {
        entity.setActive(false);
        save(entity);
    }

    List<Group> findAllByActiveTrue();

    Optional<Group> findByIdAndActiveTrue(Long id);
}
