package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.StoreDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Store repository.
 */
public interface StoreRepository extends MongoRepository<StoreDocument, ObjectId> {

    /**
     * Find by status list.
     *
     * @param status the status
     * @return the list
     */
    List<StoreDocument> findByStatus(String status);

    /**
     * Find by id and admin id optional.
     *
     * @param id      the id
     * @param adminId the admin id
     * @return the optional
     */
// verify admin owns store
    Optional<StoreDocument> findByIdAndAdminId(ObjectId id, String adminId);
}
