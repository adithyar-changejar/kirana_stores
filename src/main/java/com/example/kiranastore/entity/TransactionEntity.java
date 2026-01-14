package com.example.kiranastore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "transactions",
        indexes = {
                @Index(name = "idx_txn_user_id", columnList = "userId"),
                @Index(name = "idx_txn_created_at", columnList = "createdAt"),
                @Index(name = "idx_txn_user_created", columnList = "userId, createdAt")
        }
)
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID transactionId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
