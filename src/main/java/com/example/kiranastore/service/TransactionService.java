package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.dao.UserDao;
import com.example.kiranastore.dto.TransactionDetailsResponseDTO;
import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.mongo.UserDocument;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;
    private final UserDao userDao;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final CurrencyConversionService currencyConversionService;

    public TransactionService(
            TransactionDao transactionDao,
            UserDao userDao,
            AccountService accountService,
            TransactionMapper transactionMapper,
            CurrencyConversionService currencyConversionService
    ) {
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
        this.currencyConversionService = currencyConversionService;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {

        UserDocument user = userDao.findById(request.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("User does not exist"));

        BigDecimal amount = request.getAmount();
        CurrencyType currency = request.getCurrency();

        if (currency == CurrencyType.USD) {
            amount = amount.multiply(
                    currencyConversionService.getUsdToInrRate()
            );
            currency = CurrencyType.INR;
        }

        var account =
                accountService.getOrCreateAccount(user.getUserId());

        accountService.applyTransaction(
                account,
                amount,
                request.getType()
        );

        TransactionEntity transaction =
                transactionMapper.toEntity(request, amount, currency);

        TransactionEntity saved =
                transactionDao.save(transaction);

        return new TransactionResponseDTO(
                saved.getTransactionId(),
                saved.getStatus()
        );
    }

    public TransactionDetailsResponseDTO getTransaction(UUID id) {

        TransactionEntity entity = transactionDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found"));

        return new TransactionDetailsResponseDTO(
                entity.getTransactionId(),
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
