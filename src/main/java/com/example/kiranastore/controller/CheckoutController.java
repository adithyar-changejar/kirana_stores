package com.example.kiranastore.controller;

import com.example.kiranastore.dto.CheckoutResponseDTO;
import com.example.kiranastore.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CheckoutResponseDTO> checkout(Authentication auth) {

        String userId = auth.getName();

        CheckoutResponseDTO response =
                checkoutService.checkout(userId);

        return ResponseEntity.ok(response);
    }
}
