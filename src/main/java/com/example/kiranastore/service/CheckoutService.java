package com.example.kiranastore.service;

import com.example.kiranastore.dto.CheckoutResponseDTO;
import com.example.kiranastore.mongo.CartDocument;
import com.example.kiranastore.mongo.CartStatus;
import com.example.kiranastore.mongo.OrderDocument;
import com.example.kiranastore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * The type Checkout service.
 */
@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartRepository cartRepository;
    private final TransactionService transactionService;
    private final OrderService orderService;

    /**
     * Checkout flow:
     * 1. Fetch active cart
     * 2. Validate cart
     * 3. Calculate total
     * 4. Debit wallet
     * 5. Create order (snapshot)
     * 6. Close cart
     *
     * @param userId the user id
     * @return the checkout response dto
     */
    @Transactional
    public CheckoutResponseDTO checkout(String userId) {

        // 1️ Fetch active cart
        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("No active cart found"));

        // 2️ Validate cart
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // 3️ Calculate total (trusted price snapshot)
        BigDecimal total = cart.getItems().stream()
                .map(item ->
                        item.getPriceSnapshot()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4️ Wallet debit (throws if insufficient balance)
        transactionService.createCheckoutTransaction(userId, total);

        // 5️ Create ORDER (immutable snapshot)
        OrderDocument order = orderService.createOrder(cart, total);

        // 6️Close cart
        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        // ⃣ Return response with ORDER ID
        return new CheckoutResponseDTO(
                order.getId(),
                "SUCCESS",
                "Order placed successfully"
        );
    }
}
