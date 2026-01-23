package com.example.kiranastore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * The type Transaction entity.
 */
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId; // Mongo ObjectId (String)

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    /**
     * On create.
     */
    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * On update.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
