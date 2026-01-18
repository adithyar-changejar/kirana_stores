package com.example.kiranastore.repository;

import com.example.kiranastore.mongo.UserDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends MongoRepository<UserDocument, ObjectId> {

    Optional<UserDocument> findByEmail(String email);
}
