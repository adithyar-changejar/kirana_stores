package com.example.kiranastore.dto;

/**
 * The type Checkout response dto.
 */
public class CheckoutResponseDTO {

    private String cartId;
    private String status;
    private String message;

    /**
     * Instantiates a new Checkout response dto.
     *
     * @param cartId  the cart id
     * @param status  the status
     * @param message the message
     */
    public CheckoutResponseDTO(String cartId, String status, String message) {
        this.cartId = cartId;
        this.status = status;
        this.message = message;
    }

    /**
     * Gets cart id.
     *
     * @return the cart id
     */
    public String getCartId() {
        return cartId;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
