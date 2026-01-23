package com.example.kiranastore.mongo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {

    private String productId;
    private int quantity;

    // price snapshot at time of adding to cart
    private BigDecimal priceSnapshot;
}
