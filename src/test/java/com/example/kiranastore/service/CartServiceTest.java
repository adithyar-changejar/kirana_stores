package com.example.kiranastore.service;

import com.example.kiranastore.mongo.*;
import com.example.kiranastore.repository.CartRepository;
import com.example.kiranastore.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CartService
 */
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private ProductDocument product;

    @BeforeEach
    void setup() {
        product = new ProductDocument();
        product.setId(new ObjectId("507f1f77bcf86cd799439011"));
        product.setStoreId(new ObjectId("507f1f77bcf86cd799439012"));
        product.setPrice(BigDecimal.valueOf(500));
    }

    /**
     *  Add product to new cart
     */
    @Test
    void shouldCreateCartAndAddItem() {

        when(productRepository.findById(any()))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByUserIdAndStatus(
                "user-1", CartStatus.ACTIVE))
                .thenReturn(Optional.empty());

        when(cartRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        CartDocument cart =
                cartService.addToCart(
                        "user-1",
                        product.getStoreId().toHexString(),
                        product.getId().toHexString(),
                        2
                );

        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().get(0).getQuantity());
        assertEquals(
                BigDecimal.valueOf(500),
                cart.getItems().get(0).getPriceSnapshot()
        );
    }

    /**
     *  Merge quantity when same product is added again
     */
    @Test
    void shouldMergeQuantityForExistingItem() {

        CartItem item = new CartItem();
        item.setProductId(product.getId().toHexString());
        item.setQuantity(2);
        item.setPriceSnapshot(BigDecimal.valueOf(500));

        CartDocument existingCart = new CartDocument();
        existingCart.setUserId("user-1");
        existingCart.setStoreId(product.getStoreId().toHexString());
        existingCart.setStatus(CartStatus.ACTIVE);
        existingCart.setItems(new ArrayList<>());
        existingCart.getItems().add(item);

        when(productRepository.findById(any()))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByUserIdAndStatus(
                "user-1", CartStatus.ACTIVE))
                .thenReturn(Optional.of(existingCart));

        when(cartRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        CartDocument cart =
                cartService.addToCart(
                        "user-1",
                        product.getStoreId().toHexString(),
                        product.getId().toHexString(),
                        3
                );

        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    /**
     *  Reject product from another store
     */
    @Test
    void shouldFailIfProductDoesNotBelongToStore() {

        when(productRepository.findById(any()))
                .thenReturn(Optional.of(product));

        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> cartService.addToCart(
                                "user-1",
                                new ObjectId().toHexString(),
                                product.getId().toHexString(),
                                1
                        )
                );

        assertEquals(
                "Product does not belong to store",
                ex.getMessage()
        );
    }

    /**
     *  Fail when product does not exist
     */
    @Test
    void shouldFailIfProductNotFound() {


        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addToCart(
                        "user-1",
                        "store-1",
                        "product-1",
                        1
                )
        );
    }
}
