package com.expensivemanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.expensivemanager.model.Expense;
import com.expensivemanager.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ExpenseService}.
 */
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new expense")
    void testCreateExpense() {
        Expense expense = new Expense();
        expense.setTitle("Dinner");
        expense.setAmount(BigDecimal.valueOf(150));
        expense.setDate(LocalDate.now());
        expense.setCategory("Food");

        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense saved = expenseService.createExpense(expense);

        assertThat(saved.getTitle()).isEqualTo("Dinner");
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    @DisplayName("Should get expense by ID")
    void testGetExpenseById() {
        Expense expense = new Expense();
        expense.setId(1L);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        Optional<Expense> found = expenseService.getExpenseById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(1L);
    }
}
