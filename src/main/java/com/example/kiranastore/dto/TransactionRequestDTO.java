package com.example.kiranastore.dto;

import com.example.kiranastore.entity.TransactionType;
import java.math.BigDecimal;

public class TransactionRequestDTO {

    private String userId;
    private BigDecimal amount;
    private String currency;
    private TransactionType type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
