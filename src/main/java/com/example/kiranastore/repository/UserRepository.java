package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends MongoRepository<UserDocument, String> {
}
