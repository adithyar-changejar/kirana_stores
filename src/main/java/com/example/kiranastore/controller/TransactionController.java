package com.example.kiranastore.controller;

import com.example.kiranastore.dto.TransactionDetailsResponseDTO;
import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO request) {

        return ResponseEntity.ok(
                transactionService.createTransaction(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDetailsResponseDTO> getTransaction(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                transactionService.getTransaction(id)
        );
    }
}
