package com.example.kiranastore.service;

import com.example.kiranastore.dto.CartItemDTO;
import com.example.kiranastore.dto.CartResponseDTO;
import com.example.kiranastore.mongo.CartDocument;
import com.example.kiranastore.mongo.CartItem;
import com.example.kiranastore.mongo.CartStatus;
import com.example.kiranastore.mongo.ProductDocument;
import com.example.kiranastore.repository.CartRepository;
import com.example.kiranastore.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository
    ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Add product to cart
     * Price is ALWAYS fetched from DB
     */
    public CartDocument addToCart(
            String userId,
            String storeId,
            String productId,
            int quantity
    ) {

        // 1️⃣ FETCH PRODUCT
        ProductDocument product = productRepository
                .findById(new ObjectId(productId))
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));

        // 2️⃣ VALIDATE STORE OWNERSHIP
        ObjectId storeObjectId = new ObjectId(storeId);

        if (product.getStoreId() == null) {
            throw new IllegalStateException("Product storeId is missing");
        }

        if (!product.getStoreId().equals(storeObjectId)) {
            throw new IllegalArgumentException("Product does not belong to store");
        }

        // 3️⃣ TRUSTED PRICE (SERVER SIDE)
        BigDecimal priceSnapshot = product.getPrice();

        // 4️⃣ FETCH / CREATE CART
        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    CartDocument c = new CartDocument();
                    c.setUserId(userId);
                    c.setStoreId(storeId);
                    c.setStatus(CartStatus.ACTIVE);
                    c.setItems(new ArrayList<>());
                    return c;
                });

        // 5️⃣ MERGE / ADD ITEM
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setPriceSnapshot(priceSnapshot);
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }


    /**
     * Get active cart with total
     */
    public CartResponseDTO getActiveCart(String userId) {

        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("No active cart found"));

        BigDecimal total = BigDecimal.ZERO;
        List<CartItemDTO> items = new ArrayList<>();

        for (CartItem item : cart.getItems()) {

            BigDecimal subtotal =
                    item.getPriceSnapshot()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(subtotal);

            items.add(new CartItemDTO(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getPriceSnapshot(),
                    subtotal
            ));
        }

        return new CartResponseDTO(
                cart.getId(),
                cart.getUserId(),
                cart.getStoreId(),
                total,
                items
        );
    }

    /**
     * Mark cart as checked out
     */
    public void clearCart(String userId) {

        cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .ifPresent(cart -> {
                    cart.setStatus(CartStatus.CHECKED_OUT);
                    cartRepository.save(cart);
                });
    }
}
