package com.example.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Login response dto.
 */
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
}
