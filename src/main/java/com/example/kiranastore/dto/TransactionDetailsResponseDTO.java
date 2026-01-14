package com.example.kiranastore.dto;

import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionStatus;
import com.example.kiranastore.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TransactionDetailsResponseDTO {

    private UUID transactionId;
    private String userId;
    private BigDecimal amount;
    private CurrencyType currency;
    private TransactionType type;
    private TransactionStatus status;
    private Date createdAt;
    private Date updatedAt;
}
