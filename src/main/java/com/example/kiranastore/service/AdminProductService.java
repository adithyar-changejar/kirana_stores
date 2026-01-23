package com.example.kiranastore.service;

import com.example.kiranastore.entity.ProductStatus;
import com.example.kiranastore.mongo.ProductDocument;
import com.example.kiranastore.mongo.StoreDocument;
import com.example.kiranastore.repository.ProductRepository;
import com.example.kiranastore.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public ProductDocument addProduct(
            String adminId,
            String storeId,
            String name,
            BigDecimal price,
            String currency
    ) {

        ObjectId storeObjectId = new ObjectId(storeId);

        StoreDocument store = storeRepository.findById(storeObjectId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        if (!store.getAdminId().equals(adminId)) {
            throw new SecurityException("You do not own this store");
        }

        ProductDocument product = ProductDocument.builder()
                .storeId(storeObjectId)
                .name(name)
                .price(price)
                .currency(currency)
                .status(ProductStatus.ACTIVE)
                .build();

        return productRepository.save(product);
    }
}
