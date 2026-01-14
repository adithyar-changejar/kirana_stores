package com.example.kiranastore.repository;

import com.example.kiranastore.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository
        extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByUserId(String userId);

    List<TransactionEntity> findByCreatedAtBetween(
            Date from,
            Date to
    );

    List<TransactionEntity> findByUserIdAndCreatedAtBetween(
            String userId,
            Date from,
            Date to
    );

    // TODO read about indexes
}
