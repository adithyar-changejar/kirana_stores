package com.example.kiranastore.service;

import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.entity.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * The type Transaction aggregation service.
 */
@Service
public class TransactionAggregationService {

    /**
     * Aggregate aggregation result.
     *
     * @param transactions the transactions
     * @return the aggregation result
     */
    public AggregationResult aggregate(List<TransactionEntity> transactions) {

        BigDecimal credits = transactions.stream()
                .filter(t -> t.getType() == TransactionType.CREDIT)
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal debits = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DEBIT)
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal net = credits.subtract(debits);

        return new AggregationResult(
                credits.setScale(2, RoundingMode.HALF_UP).doubleValue(),
                debits.setScale(2, RoundingMode.HALF_UP).doubleValue(),
                net.setScale(2, RoundingMode.HALF_UP).doubleValue(),
                transactions.size()
        );
    }

    /**
     * The type Aggregation result.
     */
    public record AggregationResult(
            double totalCredits,
            double totalDebits,
            double netAmount,
            int totalTransactions
    ) {}
}
