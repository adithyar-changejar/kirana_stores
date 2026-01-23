package com.example.kiranastore.dao;

import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.entity.TransactionType;
import com.example.kiranastore.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Transaction dao.
 */
@Repository
public class TransactionDaoImpl implements TransactionDao {

    private final TransactionRepository transactionRepository;

    /**
     * Instantiates a new Transaction dao.
     *
     * @param transactionRepository the transaction repository
     */
    public TransactionDaoImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionEntity save(TransactionEntity entity) {
        return transactionRepository.save(entity);
    }

    @Override
    public Optional<TransactionEntity> findById(UUID id) {
        return transactionRepository.findById(id);
    }

    @Override
    public double getTotalCredits(String userId, Date from, Date to) {
        return transactionRepository.sumAmountByType(
                userId,
                TransactionType.CREDIT,
                from,
                to
        );
    }

    @Override
    public double getTotalDebits(String userId, Date from, Date to) {
        return transactionRepository.sumAmountByType(
                userId,
                TransactionType.DEBIT,
                from,
                to
        );
    }

    @Override
    public long getTransactionCount(String userId, Date from, Date to) {
        return transactionRepository
                .countByUserIdAndCreatedAtBetween(userId, from, to);
    }
}
