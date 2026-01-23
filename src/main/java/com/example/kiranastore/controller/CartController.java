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

/**
 * The type Cart controller.
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Add item to cart
     *
     * @param req  the req
     * @param auth the auth
     * @return the response entity
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
     *
     * @param authentication the authentication
     * @return the cart
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCart(Authentication authentication) {

        String userId = authentication.getName();

        return ResponseEntity.ok(
                cartService.getActiveCart(userId)
        );
    }

    /**
     * The type Add request.
     */
    @Data
    static class AddRequest {
        private String storeId;
        private String productId;
        private int quantity;
        private BigDecimal price;
    }
}
