package com.example.kiranastore.controller;

import com.example.kiranastore.dto.CreateProductRequestDTO;
import com.example.kiranastore.mongo.ProductDocument;
import com.example.kiranastore.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/stores/{storeId}/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDocument addProduct(
            @PathVariable String storeId,
            @RequestBody CreateProductRequestDTO request,
            Authentication auth
    ) {
        System.out.println("---- CONTROLLER HIT ----");

        if (auth == null) {
            System.out.println("AUTH IS NULL ❌");
        } else {
            System.out.println("AUTH CLASS = " + auth.getClass());
            System.out.println("AUTH NAME = " + auth.getName());
            System.out.println("AUTHORITIES = " + auth.getAuthorities());
        }

        return adminProductService.addProduct(
                auth.getName(),
                storeId,
                request.getName(),
                request.getPrice(),
                request.getCurrency()
        );
    }

}
