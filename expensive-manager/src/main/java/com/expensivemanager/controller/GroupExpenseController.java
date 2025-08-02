package com.expensivemanager.controller;

import com.expensivemanager.model.Expense;
import com.expensivemanager.service.GroupExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
/**
 * REST controller for group expense operations.
 * Endpoints use the core Expense model and service.
 */
@RestController
@RequestMapping("/groups/{groupId}/expenses")
public class GroupExpenseController {

    @Autowired
    private GroupExpenseService groupExpenseService;

    /**
     * Adds a new expense for the specified group.
     * The request body must include: title, amount, payerId, and optionally category.
     *
     * Example Request Body:
     * {
     *   "title": "Dinner",
     *   "amount": 1000.00,
     *   "payerId": 3,
     *   "category": "Food"
     * }
     *
     * @param groupId Path variable for group
     * @param req Request DTO for group expense
     * @return The created expense
     */
    @PostMapping
    public ResponseEntity<Expense> addGroupExpense(
            @PathVariable Long groupId,
            @RequestBody GroupExpenseRequest req
    ) {
        Expense expense = groupExpenseService.addGroupExpense(
                groupId,
                req.getPayerId(),
                req.getTitle(),
                req.getAmount(),
                req.getCategory()
        );
        return ResponseEntity.ok(expense);
    }

    /**
     * Lists all expenses for the specified group.
     *
     * @param groupId Path variable for group
     * @return List of group expenses
     */
    @GetMapping
    public ResponseEntity<List<Expense>> getGroupExpenses(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupExpenseService.getExpensesForGroup(groupId));
    }

     /**
     * Request DTO for creating a group expense.
     */
    public static class GroupExpenseRequest {
        private String title;
        private BigDecimal amount;
        private Long payerId;
        private String category;

        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        public Long getPayerId() { return payerId; }
        public void setPayerId(Long payerId) { this.payerId = payerId; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
}
