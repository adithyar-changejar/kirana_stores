package com.example.kiranastore.mongo;

import com.example.kiranastore.entity.ProductStatus;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductDocument {

    @Id
    private ObjectId id;

    private ObjectId storeId;

    private String name;

    private BigDecimal price;

    private String currency; // keep String for now (INR)

    private ProductStatus status;
}
