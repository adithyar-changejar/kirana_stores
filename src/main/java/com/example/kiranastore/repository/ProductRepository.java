package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.ProductDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * The interface Product repository.
 */
public interface ProductRepository extends MongoRepository<ProductDocument, ObjectId> {

    /**
     * Find by store id and status list.
     *
     * @param storeId the store id
     * @param status  the status
     * @return the list
     */
    List<ProductDocument> findByStoreIdAndStatus(ObjectId storeId, String status);
}
