package com.expensivemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensivemanager.model.Expense;

/**
 * Repository interface for {@link Expense} entities.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    /**
     * Finds all expenses for a given user.
     *
     * @param userId ID of the user
     * @return List of expenses belonging to the user
     */
    List<Expense> findByUserId(Long userId);

     /**
     * Finds all expenses for a given group.
     *
     * @param groupId ID of the group
     * @return List of expenses belonging to the group
     */
    List<Expense> findByGroupId(Long groupId);

}
