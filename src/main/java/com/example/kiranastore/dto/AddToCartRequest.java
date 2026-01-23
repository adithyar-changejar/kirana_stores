package com.example.kiranastore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * The type Add to cart request.
 */
public class AddToCartRequest {

    @NotBlank
    private String storeId;

    @NotBlank
    private String productId;

    @Min(1)
    private int quantity;

    /**
     * Gets store id.
     *
     * @return the store id
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
}
