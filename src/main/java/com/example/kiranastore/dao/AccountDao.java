package com.example.kiranastore.dao;

import com.example.kiranastore.entity.AccountEntity;

import java.util.Optional;

/**
 * The interface Account dao.
 */
public interface AccountDao {

    /**
     * Save account entity.
     *
     * @param account the account
     * @return the account entity
     */
    AccountEntity save(AccountEntity account);

    /**
     * Find by user id optional.
     *
     * @param userId the user id
     * @return the optional
     */
    Optional<AccountEntity> findByUserId(String userId);
}
