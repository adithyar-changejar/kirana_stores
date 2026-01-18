package com.example.kiranastore.service;

import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.entity.TransactionStatus;
import com.example.kiranastore.entity.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TransactionMapper {

    /**
     * Maps request data to TransactionEntity
     * userId = Mongo ObjectId (hex string)
     */
    public TransactionEntity toEntity(
            BigDecimal amount,
            CurrencyType currency,
            TransactionType type,
            String userId
    ) {

        TransactionEntity entity = new TransactionEntity();
        entity.setUserId(userId);
        entity.setAmount(amount);
        entity.setCurrency(currency);
        entity.setType(type);
        entity.setStatus(TransactionStatus.SUCCESS);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());

        return entity;
    }
}
