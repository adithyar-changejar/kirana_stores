package com.example.kiranastore.service;

import com.example.kiranastore.entity.StoreStatus;
import com.example.kiranastore.entity.UserRole;
import com.example.kiranastore.mongo.StoreDocument;
import com.example.kiranastore.mongo.UserDocument;
import com.example.kiranastore.repository.StoreRepository;
import com.example.kiranastore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // =========================
    // PUBLIC – list active stores
    // =========================
    public List<StoreDocument> getActiveStores() {
        return storeRepository.findByStatus(StoreStatus.ACTIVE.name());
    }

    // =========================
    // SUPER_ADMIN – create store + assign ADMIN
    // =========================
    public StoreDocument createStoreBySuperAdmin(
            String storeName,
            String adminId
    ) {

        // validate adminId
        ObjectId adminObjectId;
        try {
            adminObjectId = new ObjectId(adminId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid adminId");
        }

        UserDocument admin = userRepository.findById(adminObjectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Admin user not found"));

        if (admin.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("User is not an ADMIN");
        }

        StoreDocument store = StoreDocument.builder()
                .name(storeName)
                .adminId(adminId)
                .status(StoreStatus.ACTIVE)
                .build();

        return storeRepository.save(store);
    }
}
