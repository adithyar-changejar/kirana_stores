package com.example.kiranastore.dao;

import com.example.kiranastore.entity.TransactionEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Transaction dao.
 */
public interface TransactionDao {

    /**
     * Save transaction entity.
     *
     * @param entity the entity
     * @return the transaction entity
     */
    TransactionEntity save(TransactionEntity entity);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<TransactionEntity> findById(UUID id);

    /**
     * Gets total credits.
     *
     * @param userId the user id
     * @param from   the from
     * @param to     the to
     * @return the total credits
     */
    double getTotalCredits(String userId, Date from, Date to);

    /**
     * Gets total debits.
     *
     * @param userId the user id
     * @param from   the from
     * @param to     the to
     * @return the total debits
     */
    double getTotalDebits(String userId, Date from, Date to);

    /**
     * Gets transaction count.
     *
     * @param userId the user id
     * @param from   the from
     * @param to     the to
     * @return the transaction count
     */
    long getTransactionCount(String userId, Date from, Date to);
}
