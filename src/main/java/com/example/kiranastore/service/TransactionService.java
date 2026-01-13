package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;
    private final CurrencyConversionService currencyConversionService;

    public TransactionService(
            TransactionDao transactionDao,
            CurrencyConversionService currencyConversionService
    ) {
        this.transactionDao = transactionDao;
        this.currencyConversionService = currencyConversionService;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {

        // ===== VALIDATION =====
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

        BigDecimal amount = request.getAmount();
        String currency = request.getCurrency();

        // ===== CURRENCY CONVERSION =====
        if ("USD".equalsIgnoreCase(currency)) {
            BigDecimal rate = currencyConversionService.getUsdToInrRate();
            amount = amount.multiply(rate);
            currency = "INR";
        }

        // ===== DTO → ENTITY =====
        TransactionEntity entity = new TransactionEntity();
        entity.setUserId(request.getUserId());
        entity.setAmount(amount);
        entity.setCurrency(currency);
        entity.setType(request.getType());
        entity.setStatus("SUCCESS");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        TransactionEntity saved = transactionDao.save(entity);

        // ===== ENTITY → RESPONSE DTO =====
        return new TransactionResponseDTO(
                saved.getTransactionId(),
                saved.getStatus()
        );
    }

    public TransactionEntity getTransaction(UUID id) {
        return transactionDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found"));
    }
}
