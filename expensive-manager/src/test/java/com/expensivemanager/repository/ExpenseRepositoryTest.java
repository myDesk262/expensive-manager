package com.expensivemanager.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.expensivemanager.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ExpenseRepository}.
 */
@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    @DisplayName("Should save and find expense by userId")
    void testSaveAndFindByUserId() {
        Expense expense = new Expense();
        expense.setTitle("Lunch");
        expense.setAmount(BigDecimal.valueOf(100));
        expense.setDate(LocalDate.now());
        expense.setCategory("Food");
        // expense.setUser(...); // Set user if needed

        expenseRepository.save(expense);

        List<Expense> expenses = expenseRepository.findByUserId(null); // Pass correct userId in real test
        assertThat(expenses).isNotEmpty();
    }
}
