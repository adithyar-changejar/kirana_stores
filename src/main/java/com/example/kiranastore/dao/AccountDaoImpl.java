package com.example.kiranastore.dao;

import com.example.kiranastore.entity.AccountEntity;
import com.example.kiranastore.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The type Account dao.
 */
@Repository
public class AccountDaoImpl implements AccountDao {

    private final AccountRepository accountRepository;

    /**
     * Instantiates a new Account dao.
     *
     * @param accountRepository the account repository
     */
    public AccountDaoImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountEntity save(AccountEntity account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<AccountEntity> findByUserId(String userId) {
        return accountRepository.findByUserId(userId);
    }
}
