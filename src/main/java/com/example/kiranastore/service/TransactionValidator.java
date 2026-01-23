package com.example.kiranastore.service;

import com.example.kiranastore.dto.TransactionRequestDTO;
import org.springframework.stereotype.Component;

/**
 * The type Transaction validator.
 */
@Component
public class TransactionValidator {

    /**
     * Validate.
     *
     * @param request the request
     */
    public void validate(TransactionRequestDTO request) {

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
