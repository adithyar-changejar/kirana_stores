package com.example.kiranastore.service;

import com.example.kiranastore.entity.UserRole;
import com.example.kiranastore.mongo.UserDocument;
import com.example.kiranastore.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void promoteToAdmin(String userId) {

        ObjectId objectId;
        try {
            objectId = new ObjectId(userId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid userId");
        }

        UserDocument user = userRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🚫 Only USER → ADMIN
        if (user.getRole() != UserRole.USER) {
            throw new RuntimeException("Only USER can be promoted");
        }

        user.setRole(UserRole.ADMIN);
        user.setUpdatedAt(new Date());

        userRepository.save(user);
    }
}
