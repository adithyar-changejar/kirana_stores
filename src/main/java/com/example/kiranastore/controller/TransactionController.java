package com.example.kiranastore.controller;

import com.example.kiranastore.dto.TransactionRequestDTO;
import com.example.kiranastore.dto.TransactionResponseDTO;
import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {


    /*
- Transaction API entry
- Handle create request
- Delegate to service
- Return response
*/

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody TransactionRequestDTO request) {

        TransactionResponseDTO response =
                transactionService.createTransaction(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionEntity> getTransaction(
            @PathVariable UUID id) {

        TransactionEntity transaction =
                transactionService.getTransaction(id);

        return ResponseEntity.ok(transaction);
    }
}

// TODO shouldn't take entity as request
// TODO should send desired response not entity