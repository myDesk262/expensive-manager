package com.expensivemanager.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensivemanager.model.Expense;
import com.expensivemanager.service.ExpenseService;

/**
 * REST controller for expense-related operations.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    /* /**
     * each new expense will be associated with the currently logged-in user.
     * When to Include/Remove Principal
     *   Include if your API is secured and you want to associate data with a user.

     *  Remove if your app is public or user association is not required (or handle it another way).
     *
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            expense.setUser(user);
        }
        Expense saved = expenseService.createExpense(expense);
        logger.info("Expense saved: {}", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    } */


    /**
     * Creates a new expense.
     *
     * @param expense   Expense data from the request body
     * @param principal The currently authenticated user
     * @return The created expense
     */
    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, Principal principal) {
        // Optionally set user based on principal if needed
        Expense saved = expenseService.createExpense(expense);
        logger.info("Expense saved: {}", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Retrieves all expenses for a user.
     *
     * @param userId ID of the user
     * @return List of expenses for the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getExpensesByUserId(userId));
    }

    /**
     * Retrieves an expense by its ID.
     *
     * @param id ID of the expense
     * @return The expense, if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable Long id) {
        return expenseService.getExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes an expense by its ID.
     *
     * @param id ID of the expense
     * @return HTTP 204 No Content if deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates an existing expense by ID.
     *
     * @param id The ID of the expense to update.
     * @param updatedExpense The new data for the expense.
     * @return The updated expense, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable Long id,
            @RequestBody Expense updatedExpense) {
        return expenseService.updateExpense(id, updatedExpense)
                .map(expense -> ResponseEntity.ok().body(expense))
                .orElse(ResponseEntity.notFound().build());
    }

}
