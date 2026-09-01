package com.example.transactionstarter;

import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.service.TransactionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionServiceTests {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    // Test 1: Successful transaction creation
    @Test
    void shouldCreateTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TEST001",
                "CUST001",
                new BigDecimal("500.00"),
                "USD",
                "PAYMENT",
                null
        );

        Transaction saved = transactionService.createTransaction(transaction);

        assertNotNull(saved);
        assertEquals("TEST001", saved.getTransactionId());
        assertEquals("CUST001", saved.getCustomerId());
        assertEquals(new BigDecimal("500.00"), saved.getAmount());
        assertEquals("PENDING", saved.getTransactionStatus());
    }

    // Test 2: Duplicate Transaction ID should be rejected
    @Test
    void shouldRejectDuplicateTransactionId() {

        Transaction transaction = new Transaction(
                "TEST002",
                "CUST002",
                new BigDecimal("100.00"),
                "USD",
                "PAYMENT",
                null
        );

        transactionService.createTransaction(transaction);

        Transaction duplicate = new Transaction(
                "TEST002",
                "CUST003",
                new BigDecimal("200.00"),
                "USD",
                "PAYMENT",
                null
        );

        assertThrows(
                DuplicateTransactionException.class,
                () -> transactionService.createTransaction(duplicate)
        );
    }

    // Test 3: Transaction status should be updated
    @Test
    void shouldUpdateTransactionStatus() {

        Transaction transaction = new Transaction(
                "TEST003",
                "CUST003",
                new BigDecimal("250.00"),
                "USD",
                "PAYMENT",
                null
        );

        transactionService.createTransaction(transaction);

        Transaction updated =
                transactionService.updateStatus("TEST003", "COMPLETED");

        assertEquals("COMPLETED", updated.getTransactionStatus());
    }

    // Test 4: Completed transaction status should not be changed
    @Test
    void shouldNotChangeCompletedTransactionStatus() {

        Transaction transaction = new Transaction(
                "TEST004",
                "CUST004",
                new BigDecimal("300.00"),
                "USD",
                "PAYMENT",
                null
        );

        transactionService.createTransaction(transaction);

        transactionService.updateStatus("TEST004", "COMPLETED");

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.updateStatus("TEST004", "FAILED")
        );
    }

    // Test 5: Missing transaction should be rejected
    @Test
    void shouldThrowExceptionWhenTransactionDoesNotExist() {

        assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.getTransaction("DOES_NOT_EXIST")
        );
    }

    // Test 6: Invalid amount should be rejected by validation
    @Test
    void shouldRejectTransactionWithInvalidAmount() throws Exception {

        String requestBody = """
                {
                    "transactionId": "TEST005",
                    "customerId": "CUST005",
                    "amount": 0.00,
                    "currency": "USD",
                    "transactionType": "PAYMENT"
                }
                """;

        mockMvc.perform(
                post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(status().isBadRequest());
    }
}