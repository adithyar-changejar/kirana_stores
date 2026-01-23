package com.example.kiranastore.dao;

import com.example.kiranastore.mongo.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User dao.
 */
@Repository
public interface UserDao extends MongoRepository<UserDocument, String> {
}
