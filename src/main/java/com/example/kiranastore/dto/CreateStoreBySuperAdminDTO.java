package com.example.kiranastore.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateStoreBySuperAdminDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String adminId;

    public String getName() {
        return name;
    }

    public String getAdminId() {
        return adminId;
    }
}
