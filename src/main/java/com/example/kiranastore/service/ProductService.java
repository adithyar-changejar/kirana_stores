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

import java.util.List;

/**
 * The type Product service.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    /**
     * ADMIN → Add product to OWN store
     *
     * @param adminId the admin id
     * @param storeId the store id
     * @param request the request
     * @return the product document
     */
    public ProductDocument createProduct(
            String adminId,
            String storeId,
            CreateProductRequestDTO request
    ) {

        ObjectId storeObjectId = new ObjectId(storeId);

        //  verify store belongs to admin
        StoreDocument store = storeRepository
                .findByIdAndAdminId(storeObjectId, adminId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Admin does not own this store"));

        ProductDocument product = ProductDocument.builder()
                .storeId(store.getId())
                .name(request.getName())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .status(ProductStatus.ACTIVE)
                .build();

        return productRepository.save(product);
    }

    /**
     * PUBLIC → Get products by store
     *
     * @param storeId the store id
     * @return the products
     */
    public List<ProductDocument> getProducts(ObjectId storeId) {
        return productRepository.findByStoreIdAndStatus(
                storeId,
                ProductStatus.ACTIVE.name()
        );
    }
}
