package com.example.kiranastore.service;

import com.example.kiranastore.dao.AccountDao;
import com.example.kiranastore.entity.AccountEntity;
import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The type Account service.
 */
@Service
public class AccountService {

    private final AccountDao accountDao;

    /**
     * Instantiates a new Account service.
     *x
     * @param accountDao the account dao
     */
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Gets or create account.
     *
     * @param userId the user id
     * @return the or create account
     */
    @Transactional
    public AccountEntity getOrCreateAccount(String userId) {

        return accountDao.findByUserId(userId)
                .orElseGet(() -> {
                    AccountEntity account = new AccountEntity();
                    account.setUserId(userId);
                    account.setBalance(BigDecimal.ZERO);
                    account.setCurrency(CurrencyType.INR);
                    account.setCreatedAt(new Date());
                    account.setUpdatedAt(new Date());
                    return accountDao.save(account);
                });
    }

    /**
     * Apply transaction.
     *
     * @param account the account
     * @param amount  the amount
     * @param type    the type
     */
    public void applyTransaction(
            AccountEntity account,
            BigDecimal amount,
            TransactionType type
    ) {

        if (type == TransactionType.DEBIT &&
                account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        BigDecimal updatedBalance =
                type == TransactionType.CREDIT
                        ? account.getBalance().add(amount)
                        : account.getBalance().subtract(amount);

        account.setBalance(updatedBalance);
        account.setUpdatedAt(new Date());
        accountDao.save(account);
    }
}
