package com.example.kiranastore.service;

import com.example.kiranastore.dao.InventoryDao;
import com.example.kiranastore.entity.InventoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for InventoryService
 */
@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryDao inventoryDao;

    @InjectMocks
    private InventoryService inventoryService;

    private InventoryEntity inventory;

    @BeforeEach
    void setup() {
        inventory = new InventoryEntity();
        inventory.setStoreId("store-1");
        inventory.setProductId("product-1");
        inventory.setAvailableQuantity(10);
        inventory.setCreatedAt(new Date());
        inventory.setUpdatedAt(new Date());
    }

    /**
     *  Deduct stock when sufficient quantity exists
     */
    @Test
    void shouldDeductStockSuccessfully() {

        when(inventoryDao.findForUpdate("store-1", "product-1"))
                .thenReturn(Optional.of(inventory));

        inventoryService.deductStock(
                "store-1",
                "product-1",
                3
        );

        assertEquals(7, inventory.getAvailableQuantity());
        verify(inventoryDao).save(inventory);
    }

    /**
     *  Fail when inventory does not exist
     */
    @Test
    void shouldFailWhenInventoryNotFound() {

        when(inventoryDao.findForUpdate("store-1", "product-1"))
                .thenReturn(Optional.empty());

        IllegalStateException ex =
                assertThrows(
                        IllegalStateException.class,
                        () -> inventoryService.deductStock(
                                "store-1",
                                "product-1",
                                1
                        )
                );

        assertEquals(
                "Inventory not found",
                ex.getMessage()
        );
    }

    /**
     *  Fail when stock is insufficient
     */
    @Test
    void shouldFailWhenInsufficientStock() {

        when(inventoryDao.findForUpdate("store-1", "product-1"))
                .thenReturn(Optional.of(inventory));

        IllegalStateException ex =
                assertThrows(
                        IllegalStateException.class,
                        () -> inventoryService.deductStock(
                                "store-1",
                                "product-1",
                                20
                        )
                );

        assertEquals(
                "Insufficient stock",
                ex.getMessage()
        );

        verify(inventoryDao, never()).save(any());
    }

    /**
     *  Create inventory for new product
     */
    @Test
    void shouldCreateInventory() {

        inventoryService.createInventory(
                "store-1",
                "product-1",
                50
        );

        verify(inventoryDao).save(any(InventoryEntity.class));
    }
}
