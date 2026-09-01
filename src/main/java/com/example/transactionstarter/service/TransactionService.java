package com.example.transactionstarter.service;

import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import com.example.transactionstarter.model.Transaction;
import com.example.transactionstarter.repository.TransactionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Create transaction
    public Transaction createTransaction(Transaction transaction) {

        if (transactionRepository.existsById(transaction.getTransactionId())) {
            throw new DuplicateTransactionException(
                    "Transaction ID already exists");
        }

        // Initial status is controlled by the system
        transaction.setTransactionStatus("PENDING");

        return transactionRepository.save(transaction);
    }

    // Get transaction by ID
    public Transaction getTransaction(String transactionId) {

        return transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                "Transaction not found"));
    }

    // Update transaction status
    public Transaction updateStatus(
            String transactionId, String newStatus) {

        Transaction transaction = getTransaction(transactionId);

        String currentStatus = transaction.getTransactionStatus();

        // COMPLETED and FAILED are final states
        if ("COMPLETED".equals(currentStatus) ||
            "FAILED".equals(currentStatus)) {

            throw new IllegalArgumentException(
                    "Final transaction status cannot be changed");
        }

        // Only COMPLETED or FAILED are allowed from PENDING
        if (!"COMPLETED".equals(newStatus) &&
            !"FAILED".equals(newStatus)) {

            throw new IllegalArgumentException(
                    "Invalid transaction status");
        }

        transaction.setTransactionStatus(newStatus);

        return transactionRepository.save(transaction);
    }

    // Get all transactions for a customer
    public List<Transaction> getCustomerTransactions(
            String customerId) {

        return transactionRepository.findByCustomerId(customerId);
    }
}