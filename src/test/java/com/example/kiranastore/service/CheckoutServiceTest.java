package com.example.kiranastore.service;

import com.example.kiranastore.dto.CheckoutResponseDTO;
import com.example.kiranastore.mongo.CartDocument;
import com.example.kiranastore.mongo.CartItem;
import com.example.kiranastore.mongo.CartStatus;
import com.example.kiranastore.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CheckoutService
 */
@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private OrderService orderService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private CheckoutService checkoutService;

    private CartDocument cart;

    @BeforeEach
    void setup() {
        CartItem item1 = new CartItem();
        item1.setProductId("prod-1");
        item1.setQuantity(2);
        item1.setPriceSnapshot(BigDecimal.valueOf(500));

        CartItem item2 = new CartItem();
        item2.setProductId("prod-2");
        item2.setQuantity(1);
        item2.setPriceSnapshot(BigDecimal.valueOf(100));

        cart = new CartDocument();
        cart.setId("cart-123");
        cart.setUserId("user-123");
        cart.setStoreId("store-123");
        cart.setStatus(CartStatus.ACTIVE);
        cart.setItems(List.of(item1, item2));
    }


    @Test
    void shouldCheckoutSuccessfully() {

        when(cartRepository.findByUserIdAndStatus(
                "user-123",
                CartStatus.ACTIVE
        )).thenReturn(Optional.of(cart));

        CheckoutResponseDTO response =
                checkoutService.checkout("user-123");

        //  total = (2 × 500) + (1 × 100) = 1100
        BigDecimal expectedTotal = BigDecimal.valueOf(1100);

        // inventory deducted per item
        verify(inventoryService)
                .deductStock("store-123", "prod-1", 2);
        verify(inventoryService)
                .deductStock("store-123", "prod-2", 1);

        // wallet debited
        verify(transactionService)
                .createCheckoutTransaction("user-123", expectedTotal);

        // cart closed
        assertEquals(CartStatus.CHECKED_OUT, cart.getStatus());
        verify(cartRepository).save(cart);

        // response check
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("Checkout completed successfully", response.getMessage());
        assertEquals("cart-123", response.getCartId());
    }
}
