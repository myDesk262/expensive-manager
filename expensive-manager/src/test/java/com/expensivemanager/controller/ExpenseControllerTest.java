package com.expensivemanager.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.expensivemanager.config.JwtAuthenticationFilter;
import com.expensivemanager.model.Expense;
import com.expensivemanager.service.ExpenseService;
import com.expensivemanager.service.JwtUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link ExpenseController}.
 */
@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("POST /api/expenses - Create new expense")
    void testCreateExpense() throws Exception {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setTitle("Snacks");
        expense.setAmount(BigDecimal.valueOf(50));
        expense.setDate(LocalDate.now());
        expense.setCategory("Food");

        when(expenseService.createExpense(any(Expense.class))).thenReturn(expense);

        String expenseJson = "{\"title\":\"Snacks\",\"amount\":50,\"date\":\"" + LocalDate.now() + "\",\"category\":\"Food\"}";

        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expenseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Snacks"));
    }

    @Test
    @DisplayName("GET /api/expenses/{id} - Find by ID")
    void testGetExpenseById() throws Exception {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setTitle("Breakfast");
        expense.setAmount(BigDecimal.valueOf(80));
        expense.setDate(LocalDate.now());
        expense.setCategory("Food");

        when(expenseService.getExpenseById(1L)).thenReturn(Optional.of(expense));

        mockMvc.perform(get("/api/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Breakfast"));
    }

    @Test
    @DisplayName("GET /api/expenses/user/{userId} - List user's expenses")
    void testGetExpensesByUser() throws Exception {
        Expense expense1 = new Expense();
        expense1.setTitle("Lunch");
        expense1.setAmount(BigDecimal.valueOf(100));
        expense1.setDate(LocalDate.now());
        expense1.setCategory("Food");

        when(expenseService.getExpensesByUserId(1L)).thenReturn(Arrays.asList(expense1));

        mockMvc.perform(get("/api/expenses/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Lunch"));
    }
}
