package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.ReportDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The interface Report repository.
 */
public interface ReportRepository
        extends MongoRepository<ReportDocument, ObjectId> {
}
