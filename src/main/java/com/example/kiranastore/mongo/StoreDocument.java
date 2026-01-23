package com.example.kiranastore.mongo;

import com.example.kiranastore.entity.StoreStatus;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Store document.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stores")
public class StoreDocument {

    @Id
    private ObjectId id;

    private String name;

    private String adminId; // THIS IS THE OWNER (USER ID)

    private StoreStatus status;
}
