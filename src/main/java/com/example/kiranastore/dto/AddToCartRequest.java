package com.example.kiranastore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AddToCartRequest {

    @NotBlank
    private String storeId;

    @NotBlank
    private String productId;

    @Min(1)
    private int quantity;

    public String getStoreId() {
        return storeId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
