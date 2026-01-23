package com.example.kiranastore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * The type Create product request dto.
 */
public class CreateProductRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @NotBlank
    private String currency; // INR

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }
}
