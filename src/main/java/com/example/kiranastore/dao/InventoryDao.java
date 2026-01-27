package com.example.kiranastore.dao;

import com.example.kiranastore.entity.InventoryEntity;

import java.util.Optional;

public interface InventoryDao {

    Optional<InventoryEntity> findForUpdate(
            String storeId,
            String productId
    );

    InventoryEntity save(InventoryEntity inventory);
}
