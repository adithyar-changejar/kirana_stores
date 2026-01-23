package com.example.kiranastore.mongo;

import com.example.kiranastore.mongo.CartStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "carts")
public class CartDocument {

    @Id
    private String id;

    private String userId;

    private String storeId;

    private CartStatus status;

    private List<CartItem> items;
}
