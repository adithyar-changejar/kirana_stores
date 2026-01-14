package com.example.kiranastore.service;

import com.example.kiranastore.dto.TransactionRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {

    public void validate(TransactionRequestDTO request) {

        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }

        if (request.getAmount() == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (request.getCurrency() == null) {
            throw new IllegalArgumentException("Currency is required");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }
    }
}
