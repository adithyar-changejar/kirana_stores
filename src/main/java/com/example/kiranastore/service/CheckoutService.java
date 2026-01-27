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
    private final InventoryService inventoryService;

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

        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("No active cart found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        //  INVENTORY CHECK + DEDUCTION
        cart.getItems().forEach(item ->
                inventoryService.deductStock(
                        cart.getStoreId(),
                        item.getProductId(),
                        item.getQuantity()
                )
        );

        BigDecimal total = cart.getItems().stream()
                .map(i -> i.getPriceSnapshot()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        transactionService.createCheckoutTransaction(userId, total);

        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return new CheckoutResponseDTO(
                cart.getId(),
                "SUCCESS",
                "Checkout completed successfully"
        );
    }

}
