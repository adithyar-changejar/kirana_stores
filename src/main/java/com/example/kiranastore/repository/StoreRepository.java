package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.StoreDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends MongoRepository<StoreDocument, ObjectId> {

    List<StoreDocument> findByStatus(String status);

    // ✅ NEW: verify admin owns store
    Optional<StoreDocument> findByIdAndAdminId(ObjectId id, String adminId);
}
