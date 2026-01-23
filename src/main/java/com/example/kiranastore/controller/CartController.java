package com.example.kiranastore.controller;

import com.example.kiranastore.dto.AddToCartRequest;
import com.example.kiranastore.service.CartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Add item to cart
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToCart(
            @RequestBody AddToCartRequest req,
            Authentication auth
    ) {
        String userId = auth.getName();

        return ResponseEntity.ok(
                cartService.addToCart(
                        userId,
                        req.getStoreId(),
                        req.getProductId(),
                        req.getQuantity()
                )
        );
    }


    /**
     * View cart + total
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCart(Authentication authentication) {

        String userId = authentication.getName();

        return ResponseEntity.ok(
                cartService.getActiveCart(userId)
        );
    }

    @Data
    static class AddRequest {
        private String storeId;
        private String productId;
        private int quantity;
        private BigDecimal price;
    }
}
