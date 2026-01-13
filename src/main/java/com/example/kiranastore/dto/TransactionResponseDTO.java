package com.example.kiranastore.dto;

import java.util.UUID;

public class TransactionResponseDTO {

    private UUID transactionId;
    private String status;

    public TransactionResponseDTO(UUID transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }
}
