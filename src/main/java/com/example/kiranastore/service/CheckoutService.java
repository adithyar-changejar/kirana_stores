package com.example.kiranastore.service;

import com.example.kiranastore.dto.CheckoutResponseDTO;
import com.example.kiranastore.mongo.CartDocument;
import com.example.kiranastore.mongo.CartStatus;
import com.example.kiranastore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepository;
    private final TransactionService transactionService;

    @Transactional
    public CheckoutResponseDTO checkout(String userId) {

        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("No active cart found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        BigDecimal total = cart.getItems().stream()
                .map(i -> i.getPriceSnapshot()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 💳 wallet debit + transaction
        transactionService.createCheckoutTransaction(userId, total);

        // ✅ close cart
        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return new CheckoutResponseDTO(
                cart.getId(),
                "SUCCESS",
                "Checkout completed successfully"
        );
    }
}
