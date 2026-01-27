package com.example.kiranastore.repository;

import com.example.kiranastore.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository
        extends JpaRepository<InventoryEntity, UUID> {

    Optional<InventoryEntity> findByStoreIdAndProductId(
            String storeId,
            String productId
    );
}
