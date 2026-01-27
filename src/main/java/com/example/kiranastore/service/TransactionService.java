package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.dto.TransactionDetailsResponseDTO;
import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.entity.TransactionType;
import com.example.kiranastore.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * The type Transaction service.
 */
@Slf4j
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
     * USER-INITIATED TRANSACTION
     * Wallet top-up / manual credit or debit
     * INPUT: USD or INR
     * STORAGE: INR ONLY
     */
    @Transactional
    public TransactionResponseDTO createTransaction(
            String userId,
            TransactionRequestDTO request
    ) {

        log.info(
                "Transaction request | userId={} amount={} currency={} type={}",
                userId,
                request.getAmount(),
                request.getCurrency(),
                request.getType()
        );

        // 1️⃣ ensure user exists
        userRepository.findById(new ObjectId(userId))
                .orElseThrow(() ->
                        new IllegalArgumentException("User does not exist"));

        // 2️⃣ normalize amount to INR
        BigDecimal amountInInr = request.getAmount();

        if (request.getCurrency() == CurrencyType.USD) {
            amountInInr = amountInInr.multiply(
                    currencyConversionService.getUsdToInrRate()
            );
        }

        amountInInr = amountInInr.setScale(2, RoundingMode.HALF_UP);

        // 3️⃣ wallet is INR-only
        var account = accountService.getOrCreateAccount(userId);

        accountService.applyTransaction(
                account,
                amountInInr,
                request.getType()
        );

        // 4️⃣ persist transaction (INR only)
        TransactionEntity entity =
                transactionMapper.toEntity(
                        amountInInr,
                        CurrencyType.INR,
                        request.getType(),
                        userId
                );

        TransactionEntity saved = transactionDao.save(entity);

        return new TransactionResponseDTO(
                saved.getId(),
                saved.getStatus()
        );
    }

    /**
     * CHECKOUT TRANSACTION
     * System-driven cart payment
     * ALWAYS: INR + DEBIT
     */
    @Transactional
    public TransactionResponseDTO createCheckoutTransaction(
            String userId,
            BigDecimal amount
    ) {

        log.info(
                "Checkout transaction | userId={} amount={} currency=INR type=DEBIT",
                userId,
                amount
        );

        var account = accountService.getOrCreateAccount(userId);

        accountService.applyTransaction(
                account,
                amount,
                TransactionType.DEBIT
        );

        TransactionEntity entity =
                transactionMapper.toEntity(
                        amount,
                        CurrencyType.INR,
                        TransactionType.DEBIT,
                        userId
                );

        TransactionEntity saved = transactionDao.save(entity);

        return new TransactionResponseDTO(
                saved.getId(),
                saved.getStatus()
        );
    }

    /**
     * FETCH TRANSACTION DETAILS
     */
    public TransactionDetailsResponseDTO getTransaction(UUID id) {

        log.info("Fetching transaction | transactionId={}", id);

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
