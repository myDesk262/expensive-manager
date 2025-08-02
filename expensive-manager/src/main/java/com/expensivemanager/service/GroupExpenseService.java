package com.expensivemanager.service;

import com.expensivemanager.model.Expense;
import com.expensivemanager.model.Group;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.ExpenseRepository;
import com.expensivemanager.repository.GroupRepository;
import com.expensivemanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for managing group expenses and splits.
 */
@Service
public class GroupExpenseService {

    @Autowired
    private ExpenseService expenseService; // Always use the core expense logic

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

     /**
     * Adds a new group expense. 
     * The 'user' is the payer, and the 'group' is the group associated.
     * 
     * @param groupId Group to assign the expense to.
     * @param payerId User ID of the member who paid.
     * @param title   Description/title of the expense.
     * @param amount  Amount spent.
     * @param category Category of the expense (optional).
     * @return The saved Expense entity.
     */
    public Expense addGroupExpense(Long groupId, Long payerId, String title, BigDecimal amount, String category) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        User payer = userRepository.findById(payerId)
                .orElseThrow(() -> new IllegalArgumentException("Payer (user) not found"));

        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setAmount(amount);
        expense.setDate(LocalDate.now());
        expense.setCategory(category);
        expense.setGroup(group);
        expense.setUser(payer); // Tracks who paid

        return expenseService.createExpense(expense);
    }

     /**
     * Retrieves all expenses belonging to a specific group.
     *
     * @param groupId Group ID.
     * @return List of Expense entities.
     */
    public List<Expense> getExpensesForGroup(Long groupId) {
         return expenseService.getExpensesByGroupId(groupId);
    }

    /**
     * Retrieves all expenses for a given group.
     * @param groupId The group ID.
     * @return List of Expense entities belonging to the group.
     */
    public List<Expense> getGroupExpenses(Long groupId) {
        return expenseRepository.findAll()
            .stream()
            .filter(exp -> exp.getGroup() != null && exp.getGroup().getId().equals(groupId))
            .toList();
    }

    // Optionally: Add methods for custom split logic using SplitDetail
}
