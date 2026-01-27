package com.example.kiranastore.controller;

import com.example.kiranastore.dto.CreateProductRequestDTO;
import com.example.kiranastore.mongo.ProductDocument;
import com.example.kiranastore.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/stores/{storeId}/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDocument addProduct(
            @PathVariable String storeId,
            @RequestBody @Valid CreateProductRequestDTO request,
            Authentication auth
    ) {
        System.out.println("---- CONTROLLER HIT ----");
        System.out.println("ADMIN ID = " + auth.getName());
        System.out.println("AUTHORITIES = " + auth.getAuthorities());

        return adminProductService.addProduct(
                auth.getName(),   // adminId
                storeId,
                request           // ✅ pass whole DTO
        );
    }
}
