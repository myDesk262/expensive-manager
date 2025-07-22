package com.expensivemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

/**
 * Data Transfer Object for Expense.
 */
@Data
public class ExpenseDTO {
    /**
     * Title or description of the expense.
     */
    private String title;

    /**
     * Amount spent.
     */
    private BigDecimal amount;

    /**
     * Date of the expense.
     */
    private LocalDate date;

    /**
     * Expense category.
     */
    private String category;

}

