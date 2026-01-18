package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.dto.TransactionDetailsResponseDTO;
import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.entity.TransactionType;
import com.example.kiranastore.mongo.UserDocument;
import com.example.kiranastore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final CurrencyConversionService currencyConversionService;

    public TransactionService(
            TransactionDao transactionDao,
            UserRepository userRepository,
            AccountService accountService,
            TransactionMapper transactionMapper,
            CurrencyConversionService currencyConversionService
    ) {
        this.transactionDao = transactionDao;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
        this.currencyConversionService = currencyConversionService;
    }

    /**
     * Create transaction for authenticated user
     * userId is derived from JWT (Mongo ObjectId hex string)
     */
    public TransactionResponseDTO createTransaction(
            String userId,
            TransactionRequestDTO request
    ) {

        // 🔒 Ensure user exists
        userRepository.findById(
                new org.bson.types.ObjectId(userId)
        ).orElseThrow(() ->
                new IllegalArgumentException("User does not exist"));

        BigDecimal amount = request.getAmount();
        CurrencyType currency = request.getCurrency();
        TransactionType type = request.getType();

        // 🌍 Currency conversion (USD → INR)
        if (currency == CurrencyType.USD) {
            amount = amount.multiply(
                    currencyConversionService.getUsdToInrRate()
            );
            currency = CurrencyType.INR;
        }

        // 🏦 Get or create account
        var account = accountService.getOrCreateAccount(userId);

        // 💰 Apply transaction to account
        accountService.applyTransaction(
                account,
                amount,
                type
        );

        // 🧾 Persist transaction
        TransactionEntity transaction =
                transactionMapper.toEntity(
                        amount,
                        currency,
                        type,
                        userId
                );

        TransactionEntity saved = transactionDao.save(transaction);

        return new TransactionResponseDTO(
                saved.getId(),
                saved.getStatus()
        );
    }

    /**
     * Fetch transaction by ID
     */
    public TransactionDetailsResponseDTO getTransaction(UUID id) {

        TransactionEntity entity = transactionDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found"));

        return new TransactionDetailsResponseDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getType(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
