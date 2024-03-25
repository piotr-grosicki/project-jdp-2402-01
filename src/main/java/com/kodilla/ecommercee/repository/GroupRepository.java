package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    default void delete(Group entity) {
        entity.setActive(false);
        save(entity);
    }
  
    Optional<Group> findByIdAndActiveTrue(Long id);

    List<Group> findAllByActiveTrue();
}
