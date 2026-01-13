package com.example.kiranastore.dao;

import com.example.kiranastore.entity.TransactionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionDao {

    TransactionEntity save(TransactionEntity entity);

    Optional<TransactionEntity> findById(UUID id);

    List<TransactionEntity> findByUserIdAndCreatedAtBetween(
            String userId,
            LocalDateTime from,
            LocalDateTime to
    );
}
