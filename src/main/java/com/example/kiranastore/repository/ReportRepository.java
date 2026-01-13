package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository
        extends MongoRepository<ReportDocument, String> {
}
