package com.example.kiranastore.mongo;

import com.example.kiranastore.entity.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "users")
public class UserDocument {

    @Id
    private String userId;

    private String name;

    private String password;   // BCrypt hash

    private UserRole role;

    private Date createdAt;
}
