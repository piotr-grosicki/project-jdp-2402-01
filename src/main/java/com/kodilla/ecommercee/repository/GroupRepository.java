package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    default void delete(Group entity) {
        entity.setActive(false);
        save(entity);
    }
}
