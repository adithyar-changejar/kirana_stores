package com.example.kiranastore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateProductRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @NotBlank
    private String currency; // INR

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
