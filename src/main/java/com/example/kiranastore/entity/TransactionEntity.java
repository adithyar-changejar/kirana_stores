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
                @Index(name = "idx_txn_user_id", columnList = "user_id"),
                @Index(name = "idx_txn_created_at", columnList = "created_at"),
                @Index(name = "idx_txn_user_created", columnList = "user_id, created_at")
        }
)
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;   //

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private Date createdAt;
    private Date updatedAt;
}
