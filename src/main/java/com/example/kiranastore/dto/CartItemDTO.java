package com.example.kiranastore.dto;

import java.math.BigDecimal;

/**
 * The type Cart item dto.
 */
public class CartItemDTO {

    private String productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    /**
     * Instantiates a new Cart item dto.
     *
     * @param productId the product id
     * @param quantity  the quantity
     * @param price     the price
     * @param subtotal  the subtotal
     */
    public CartItemDTO(
            String productId,
            int quantity,
            BigDecimal price,
            BigDecimal subtotal
    ) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public String getProductId() { return productId; }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() { return price; }

    /**
     * Gets subtotal.
     *
     * @return the subtotal
     */
    public BigDecimal getSubtotal() { return subtotal; }
}
