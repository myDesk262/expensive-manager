package com.expensivemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensivemanager.model.Expense;
import com.expensivemanager.repository.ExpenseRepository;

/**
 * Service class for managing expenses.
 */
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Creates and saves a new expense.
     *
     * @param expense The expense to create
     * @return The saved expense
     */
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    /**
     * Retrieves all expenses for a user.
     *
     * @param userId ID of the user
     * @return List of expenses for the user
     */
    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    /**
     * Retrieves an expense by its ID.
     *
     * @param id ID of the expense
     * @return Optional containing the expense if found
     */
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    /**
     * Deletes an expense by its ID.
     *
     * @param id ID of the expense to delete
     */
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

     /**
     * Updates an existing expense.
     *
     * @param id           ID of the expense to update
     * @param updatedExpense Updated expense data
     * @return The updated expense, or Optional.empty() if not found
     */
    public Optional<Expense> updateExpense(Long id, Expense updatedExpense) {
        return expenseRepository.findById(id).map(expense -> {
            expense.setTitle(updatedExpense.getTitle());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());
            expense.setCategory(updatedExpense.getCategory());
            // expense.setUser(updatedExpense.getUser()); // only if you want to allow user changes
            return expenseRepository.save(expense);
        });
    }
}
