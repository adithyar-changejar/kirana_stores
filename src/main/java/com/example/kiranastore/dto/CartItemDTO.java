package com.example.kiranastore.dto;

import java.math.BigDecimal;

public class CartItemDTO {

    private String productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

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

    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getSubtotal() { return subtotal; }
}
