package com.example.kiranastore.dao;

import com.example.kiranastore.entity.InventoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InventoryDaoImpl implements InventoryDao {

    private final EntityManager entityManager;

    public InventoryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<InventoryEntity> findForUpdate(
            String storeId,
            String productId
    ) {
        return entityManager.createQuery(
                        """
                        SELECT i FROM InventoryEntity i
                        WHERE i.storeId = :storeId
                          AND i.productId = :productId
                        """,
                        InventoryEntity.class
                )
                .setParameter("storeId", storeId)
                .setParameter("productId", productId)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultStream()
                .findFirst();
    }

    @Override
    public InventoryEntity save(InventoryEntity inventory) {
        return entityManager.merge(inventory);
    }
}
