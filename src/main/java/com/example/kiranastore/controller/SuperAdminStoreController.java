package com.example.kiranastore.controller;

import com.example.kiranastore.dto.CreateStoreBySuperAdminDTO;
import com.example.kiranastore.mongo.StoreDocument;
import com.example.kiranastore.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type Super admin store controller.
 */
@RestController
@RequestMapping("/superadmin/stores")
@RequiredArgsConstructor
public class SuperAdminStoreController {

    private final StoreService storeService;

    /**
     * Create store store document.
     *
     * @param request the request
     * @return the store document
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public StoreDocument createStore(
            @Valid @RequestBody CreateStoreBySuperAdminDTO request
    ) {
        return storeService.createStoreBySuperAdmin(
                request.getName(),
                request.getAdminId()
        );
    }
}
