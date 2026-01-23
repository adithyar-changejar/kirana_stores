package com.example.kiranastore.repository;

import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.entity.TransactionType;
import com.example.kiranastore.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.UUID;

/**
 * The interface Transaction repository.
 */
public interface TransactionRepository
        extends JpaRepository<TransactionEntity, UUID> {

    /**
     * Sum amount by type double.
     *
     * @param userId the user id
     * @param type   the type
     * @param from   the from
     * @param to     the to
     * @return the double
     */
    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM TransactionEntity t
        WHERE t.userId = :userId
          AND t.type = :type
          AND t.status = 'SUCCESS'
          AND t.createdAt BETWEEN :from AND :to
    """)
    double sumAmountByType(
            String userId,
            TransactionType type,
            Date from,
            Date to
    );

    /**
     * Count by user id and created at between long.
     *
     * @param userId the user id
     * @param from   the from
     * @param to     the to
     * @return the long
     */
    long countByUserIdAndCreatedAtBetween(
            String userId,
            Date from,
            Date to
    );
}
