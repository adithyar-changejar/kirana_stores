package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.entity.*;
import com.example.kiranastore.mongo.UserDocument;
import com.example.kiranastore.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionDao transactionDao;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldConvertUsdToInrAndCreditWallet() {

        String userId = "6978704ec389696ca5e4ce6c";

        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setAmount(new BigDecimal("100"));
        request.setCurrency(CurrencyType.USD);
        request.setType(TransactionType.CREDIT);

        // user exists
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(new UserDocument()));

        // FX rate
        when(currencyConversionService.getUsdToInrRate())
                .thenReturn(new BigDecimal("90"));

        // account
        AccountEntity account = new AccountEntity();
        account.setBalance(BigDecimal.ZERO);

        when(accountService.getOrCreateAccount(userId))
                .thenReturn(account);

        // mapper MUST return entity
        TransactionEntity mappedEntity = new TransactionEntity();

        when(transactionMapper.toEntity(
                any(BigDecimal.class),
                eq(CurrencyType.INR),
                eq(TransactionType.CREDIT),
                eq(userId)
        )).thenReturn(mappedEntity);

        // dao save
        TransactionEntity savedEntity = new TransactionEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setStatus(TransactionStatus.SUCCESS);

        when(transactionDao.save(mappedEntity))
                .thenReturn(savedEntity);

        // execute
        transactionService.createTransaction(userId, request);

        // verify wallet credit = 100 * 90 = 9000
        verify(accountService).applyTransaction(
                eq(account),
                argThat(a -> a.compareTo(new BigDecimal("9000")) == 0),
                eq(TransactionType.CREDIT)
        );

        verify(transactionDao).save(mappedEntity);
    }
}
