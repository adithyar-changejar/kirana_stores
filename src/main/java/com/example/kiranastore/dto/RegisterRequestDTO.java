package com.example.kiranastore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String role;   // USER / ADMIN
}
