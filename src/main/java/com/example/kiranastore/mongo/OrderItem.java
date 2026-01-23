package com.example.kiranastore.mongo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Order item.
 */
@Data
public class OrderItem {

    private String productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
