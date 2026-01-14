package com.example.kiranastore.service;

import com.example.kiranastore.dao.AccountDao;
import com.example.kiranastore.entity.AccountEntity;
import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountService {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

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
