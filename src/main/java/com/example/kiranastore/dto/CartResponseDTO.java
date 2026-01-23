package com.example.kiranastore.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponseDTO {

    private String cartId;
    private String userId;
    private String storeId;
    private BigDecimal totalAmount;
    private List<CartItemDTO> items;

    public CartResponseDTO(
            String cartId,
            String userId,
            String storeId,
            BigDecimal totalAmount,
            List<CartItemDTO> items
    ) {
        this.cartId = cartId;
        this.userId = userId;
        this.storeId = storeId;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public String getCartId() { return cartId; }
    public String getUserId() { return userId; }
    public String getStoreId() { return storeId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<CartItemDTO> getItems() { return items; }
}
