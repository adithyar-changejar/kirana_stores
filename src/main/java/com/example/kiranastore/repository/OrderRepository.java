package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderDocument, String> {

    List<OrderDocument> findByUserId(String userId);
}
