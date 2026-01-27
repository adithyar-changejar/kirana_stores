package com.example.kiranastore.service;

import com.example.kiranastore.dao.InventoryDao;
import com.example.kiranastore.entity.InventoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InventoryService {

    private final InventoryDao inventoryDao;

    public InventoryService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @Transactional
    public void deductStock(
            String storeId,
            String productId,
            int quantity
    ) {

        InventoryEntity inventory = inventoryDao
                .findForUpdate(storeId, productId)
                .orElseThrow(() ->
                        new IllegalStateException("Inventory not found"));

        if (inventory.getAvailableQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity
        );
        inventory.setUpdatedAt(new Date());

        inventoryDao.save(inventory);
    }

    @Transactional
    public void createInventory(
            String storeId,
            String productId,
            int initialQuantity
    ) {

        InventoryEntity inventory = new InventoryEntity();
        inventory.setStoreId(storeId);
        inventory.setProductId(productId);
        inventory.setAvailableQuantity(initialQuantity);
        inventory.setCreatedAt(new Date());
        inventory.setUpdatedAt(new Date());

        inventoryDao.save(inventory);
    }
}
