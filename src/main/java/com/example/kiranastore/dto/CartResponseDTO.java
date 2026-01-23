package com.example.kiranastore.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Cart response dto.
 */
public class CartResponseDTO {

    private String cartId;
    private String userId;
    private String storeId;
    private BigDecimal totalAmount;
    private List<CartItemDTO> items;

    /**
     * Instantiates a new Cart response dto.
     *
     * @param cartId      the cart id
     * @param userId      the user id
     * @param storeId     the store id
     * @param totalAmount the total amount
     * @param items       the items
     */
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

    /**
     * Gets cart id.
     *
     * @return the cart id
     */
    public String getCartId() { return cartId; }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() { return userId; }

    /**
     * Gets store id.
     *
     * @return the store id
     */
    public String getStoreId() { return storeId; }

    /**
     * Gets total amount.
     *
     * @return the total amount
     */
    public BigDecimal getTotalAmount() { return totalAmount; }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<CartItemDTO> getItems() { return items; }
}
