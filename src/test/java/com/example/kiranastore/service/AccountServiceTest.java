package com.example.kiranastore.service;

import com.example.kiranastore.dao.AccountDao;
import com.example.kiranastore.entity.AccountEntity;
import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AccountService
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountService accountService;

    private AccountEntity account;

    @BeforeEach
    void setup() {
        account = new AccountEntity();
        account.setUserId("user-123");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setCurrency(CurrencyType.INR);
    }

    /**
     *  Create account if not exists
     */
    @Test
    void shouldCreateAccountIfNotExists() {

        when(accountDao.findByUserId("user-123"))
                .thenReturn(Optional.empty());

        when(accountDao.save(any(AccountEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AccountEntity result =
                accountService.getOrCreateAccount("user-123");

        assertEquals("user-123", result.getUserId());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        assertEquals(CurrencyType.INR, result.getCurrency());
    }

    /**
     *  Credit wallet increases balance
     */
    @Test
    void shouldCreditWallet() {

        accountService.applyTransaction(
                account,
                BigDecimal.valueOf(500),
                TransactionType.CREDIT
        );

        assertEquals(
                BigDecimal.valueOf(1500),
                account.getBalance()
        );

        verify(accountDao).save(account);
    }

    /**
     *  Debit wallet decreases balance
     */
    @Test
    void shouldDebitWallet() {

        accountService.applyTransaction(
                account,
                BigDecimal.valueOf(400),
                TransactionType.DEBIT
        );

        assertEquals(
                BigDecimal.valueOf(600),
                account.getBalance()
        );

        verify(accountDao).save(account);
    }

    /**
     *  Prevent overdraft
     */
    @Test
    void shouldFailWhenInsufficientBalance() {

        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> accountService.applyTransaction(
                                account,
                                BigDecimal.valueOf(2000),
                                TransactionType.DEBIT
                        )
                );

        assertEquals(
                "Insufficient balance",
                ex.getMessage()
        );

        verify(accountDao, never()).save(any());
    }
}
