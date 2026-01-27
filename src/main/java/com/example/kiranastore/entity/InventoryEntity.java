package com.example.kiranastore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "inventory",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_store_product",
                        columnNames = {"store_id", "product_id"}
                )
        },
        indexes = {
                @Index(name = "idx_inventory_store_product", columnList = "store_id,product_id")
        }
)
@Data
public class InventoryEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    private Date createdAt;
    private Date updatedAt;
}
