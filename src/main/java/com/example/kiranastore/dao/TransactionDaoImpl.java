package com.example.kiranastore.dao;

import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    /*
- DAO implementation
- Wrap repository
- Execute DB calls
- Isolate persistence
*/
    private final TransactionRepository transactionRepository;

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
    public List<TransactionEntity> findByUserIdAndCreatedAtBetween(
            String userId,
            LocalDateTime from,
            LocalDateTime to
    ) {
        return transactionRepository
                .findByUserIdAndCreatedAtBetween(userId, from, to);
    }
}
