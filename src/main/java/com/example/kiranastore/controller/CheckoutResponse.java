package com.example.kiranastore.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Checkout response.
 */
@Data
@AllArgsConstructor
public class CheckoutResponse {
    private String cartId;
    private BigDecimal amountPaid;
    private String status;
}
