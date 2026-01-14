package com.example.kiranastore.mongo;

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
    private String userId;   // MUST match Mongo _id (String)

    private String name;

    private Date createdAt;
}
