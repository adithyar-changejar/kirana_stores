package com.example.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TransactionResponseDTO {

    private UUID transactionId;
    private String status;
}
