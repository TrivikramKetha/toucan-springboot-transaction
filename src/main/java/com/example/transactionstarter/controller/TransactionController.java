package com.example.transactionstarter.controller;

import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 1. Create transaction
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(
            @Valid @RequestBody Transaction transaction) {

        return transactionService.createTransaction(transaction);
    }

    // 2. Get transaction
    @GetMapping("/{transactionId}")
    public Transaction getTransaction(
            @PathVariable String transactionId) {

        return transactionService.getTransaction(transactionId);
    }

    // 3. Update transaction status
    @PatchMapping("/{transactionId}/status")
    public Transaction updateStatus(
            @PathVariable String transactionId,
            @RequestParam String status) {

        return transactionService.updateStatus(transactionId, status);
    }

    // 4. Get all transactions for a customer
    @GetMapping("/customer/{customerId}")
    public List<Transaction> getCustomerTransactions(
            @PathVariable String customerId) {

        return transactionService.getCustomerTransactions(customerId);
    }
}