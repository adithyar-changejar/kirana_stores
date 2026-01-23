package com.example.kiranastore.controller;

import com.example.kiranastore.dto.CheckoutResponseDTO;
import com.example.kiranastore.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * The type Checkout controller.
 */
@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * Checkout response entity.
     *
     * @param auth the auth
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CheckoutResponseDTO> checkout(Authentication auth) {

        String userId = auth.getName();

        CheckoutResponseDTO response =
                checkoutService.checkout(userId);

        return ResponseEntity.ok(response);
    }
}
