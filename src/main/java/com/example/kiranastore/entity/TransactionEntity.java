package com.example.kiranastore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "transactions")
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID transactionId;

    private String userId;

    private BigDecimal amount;

    private String currency;

    private String status;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
