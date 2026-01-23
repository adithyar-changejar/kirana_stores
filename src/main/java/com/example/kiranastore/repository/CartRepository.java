package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.CartDocument;
import com.example.kiranastore.mongo.CartStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<CartDocument, String> {
    Optional<CartDocument> findByUserIdAndStatus(String userId, CartStatus status);
}
