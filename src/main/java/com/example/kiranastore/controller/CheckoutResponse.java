package com.example.kiranastore.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CheckoutResponse {
    private String cartId;
    private BigDecimal amountPaid;
    private String status;
}
