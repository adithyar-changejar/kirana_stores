package com.example.kiranastore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "accounts",
        indexes = {
                @Index(name = "idx_account_user_id", columnList = "userId")
        }
)
@Data
public class AccountEntity {

    @Id
    @GeneratedValue
    private UUID accountId;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    private Date createdAt;
    private Date updatedAt;
}
