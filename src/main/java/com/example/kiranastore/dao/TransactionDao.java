package com.example.kiranastore.dao;

import com.example.kiranastore.entity.TransactionEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface TransactionDao {

    TransactionEntity save(TransactionEntity entity);

    Optional<TransactionEntity> findById(UUID id);

    double getTotalCredits(String userId, Date from, Date to);

    double getTotalDebits(String userId, Date from, Date to);

    long getTransactionCount(String userId, Date from, Date to);
}
