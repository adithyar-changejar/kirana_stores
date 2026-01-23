package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * The interface Order repository.
 */
public interface OrderRepository extends MongoRepository<OrderDocument, String> {

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<OrderDocument> findByUserId(String userId);
}
