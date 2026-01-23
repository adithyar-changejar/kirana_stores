package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.ProductDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<ProductDocument, ObjectId> {

    List<ProductDocument> findByStoreIdAndStatus(ObjectId storeId, String status);
}
