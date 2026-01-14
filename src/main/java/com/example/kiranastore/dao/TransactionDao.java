package com.example.kiranastore.dao;

import com.example.kiranastore.entity.TransactionEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionDao {

    /*
    - Transaction DAO
    - Persistence contract
    - Abstract DB access
    - Used by service
    */

    TransactionEntity save(TransactionEntity entity);

    Optional<TransactionEntity> findById(UUID id);

    List<TransactionEntity> findByUserIdAndCreatedAtBetween(
            String userId,
            Date from,
            Date to
    );
}
