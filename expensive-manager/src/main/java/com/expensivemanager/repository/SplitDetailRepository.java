package com.expensivemanager.repository;

import com.expensivemanager.model.SplitDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing SplitDetail entities.
 * Used to store and retrieve individual user shares for group expenses.
 */
@Repository
public interface SplitDetailRepository extends JpaRepository<SplitDetail, Long> {
    // Example custom method: find all splits for a given expense
    // List<SplitDetail> findByExpenseId(Long expenseId);

    // Example in SplitDetailRepository
    List<SplitDetail> findByExpenseId(Long expenseId);
    List<SplitDetail> findByUserId(Long userId);

}
