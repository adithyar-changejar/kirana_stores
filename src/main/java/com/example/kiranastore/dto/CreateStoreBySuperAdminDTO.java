package com.example.kiranastore.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * The type Create store by super admin dto.
 */
public class CreateStoreBySuperAdminDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String adminId;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets admin id.
     *
     * @return the admin id
     */
    public String getAdminId() {
        return adminId;
    }
}
