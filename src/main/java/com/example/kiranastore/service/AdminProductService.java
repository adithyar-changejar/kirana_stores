package com.example.kiranastore.service;

import com.example.kiranastore.dto.CreateProductRequestDTO;
import com.example.kiranastore.entity.ProductStatus;
import com.example.kiranastore.mongo.ProductDocument;
import com.example.kiranastore.mongo.StoreDocument;
import com.example.kiranastore.repository.ProductRepository;
import com.example.kiranastore.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    public ProductDocument addProduct(
            String adminId,
            String storeId,
            CreateProductRequestDTO request
    ) {

        StoreDocument store = storeRepository.findById(new ObjectId(storeId))
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        if (!store.getAdminId().equals(adminId)) {
            throw new SecurityException("You do not own this store");
        }

        // 1️Save product in MongoDB
        ProductDocument product = ProductDocument.builder()
                .storeId(new ObjectId(storeId))
                .name(request.getName())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .status(ProductStatus.ACTIVE)
                .build();

        ProductDocument savedProduct = productRepository.save(product);

        // 2️Create inventory in PostgreSQL
        inventoryService.createInventory(
                storeId,
                savedProduct.getId().toHexString(),
                request.getInitialQuantity()
        );

        return savedProduct;
    }
}
