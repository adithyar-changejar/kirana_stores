package com.example.kiranastore.dto;

import com.example.kiranastore.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {

    private String userId;
    private BigDecimal amount;
    private String currency;
    private TransactionType type;
}
