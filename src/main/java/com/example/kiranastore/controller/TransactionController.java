package com.example.kiranastore.controller;

import com.example.kiranastore.dto.TransactionDetailsResponseDTO;
import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The type Transaction controller.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Instantiates a new Transaction controller.
     *
     * @param transactionService the transaction service
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Create transaction response entity.
     *
     * @param request        the request
     * @param authentication the authentication
     * @return the response entity
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO request,
            Authentication authentication
    ) {

        String userId = authentication.getName(); // Mongo ObjectId (hex)

        return ResponseEntity.ok(
                transactionService.createTransaction(userId, request)
        );
    }

    /**
     * Gets transaction.
     *
     * @param id the id
     * @return the transaction
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDetailsResponseDTO> getTransaction(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                transactionService.getTransaction(id)
        );
    }
}
