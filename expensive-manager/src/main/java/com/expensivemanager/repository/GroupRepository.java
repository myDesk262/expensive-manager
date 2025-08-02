package com.expensivemanager.repository;

import com.expensivemanager.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing Group entities.
 * Provides basic CRUD operations for Group.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // You can add custom query methods here, if needed.
}
