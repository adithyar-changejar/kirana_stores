package com.example.kiranastore.dto;

import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Currency is required")
    private CurrencyType currency;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;
}
