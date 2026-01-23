package com.example.kiranastore.dto;

public class CheckoutResponseDTO {

    private String cartId;
    private String status;
    private String message;

    public CheckoutResponseDTO(String cartId, String status, String message) {
        this.cartId = cartId;
        this.status = status;
        this.message = message;
    }

    public String getCartId() {
        return cartId;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
