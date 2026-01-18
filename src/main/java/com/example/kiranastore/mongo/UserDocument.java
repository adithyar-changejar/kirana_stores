package com.example.kiranastore.mongo;

import com.example.kiranastore.entity.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "users")
public class UserDocument {

    @Id
    private ObjectId id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private UserRole role;

    private Date createdAt;
    private Date updatedAt;
}
