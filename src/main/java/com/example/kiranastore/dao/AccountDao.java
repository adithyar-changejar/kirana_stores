package com.example.kiranastore.dao;

import com.example.kiranastore.entity.AccountEntity;

import java.util.Optional;

public interface AccountDao {

    AccountEntity save(AccountEntity account);

    Optional<AccountEntity> findByUserId(String userId);
}
