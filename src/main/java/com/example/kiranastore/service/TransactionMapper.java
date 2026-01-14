package com.example.kiranastore.service;

import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.entity.CurrencyType;
import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.entity.TransactionStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TransactionMapper {

    public TransactionEntity toEntity(
            TransactionRequestDTO request,
            BigDecimal amount,
            CurrencyType currency
    ) {

        TransactionEntity entity = new TransactionEntity();
        entity.setUserId(request.getUserId());
        entity.setAmount(amount);
        entity.setCurrency(currency);
        entity.setType(request.getType());
        entity.setStatus(TransactionStatus.SUCCESS);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());

        return entity;
    }
}
