package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface GroupRepository extends JpaRepository<Group, Long> {
}
