package com.expensivemanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;  

/**
 * Entity representing an expense record for a user.
 */
@Entity @Data
@Table(name = "expenses")
public class Expense {

    /**
     * Unique identifier for the expense.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title or description of the expense.
     */
    private String title;

    /**
     * Amount spent.
     */
    private BigDecimal amount;

    /**
     * Date when the expense was made.
     */
    private LocalDate date;

    /**
     * Expense category (e.g., Food, Travel).
     */
    private String category;

    /**
     * User to whom this expense belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Expense group (e.g., Family, Friends, Personal/Self).
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;

    // For personal expenses, group is null.
    // For group expenses, link to group and record splits.


}
