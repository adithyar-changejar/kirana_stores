package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.CartDocument;
import com.example.kiranastore.mongo.CartStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * The interface Cart repository.
 */
public interface CartRepository extends MongoRepository<CartDocument, String> {
    /**
     * Find by user id and status optional.
     *
     * @param userId the user id
     * @param status the status
     * @return the optional
     */
    Optional<CartDocument> findByUserIdAndStatus(String userId, CartStatus status);
}
