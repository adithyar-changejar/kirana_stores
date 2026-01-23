package com.example.kiranastore.controller;

import com.example.kiranastore.dto.CreateProductRequestDTO;
import com.example.kiranastore.mongo.ProductDocument;
import com.example.kiranastore.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    //  PUBLIC: list products
    @GetMapping("/stores/{storeId}/products")
    public List<ProductDocument> getProducts(@PathVariable String storeId) {
        return productService.getProducts(new ObjectId(storeId));
    }
}
