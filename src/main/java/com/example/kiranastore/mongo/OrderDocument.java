package com.example.kiranastore.mongo;

import com.example.kiranastore.entity.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The type Order document.
 */
@Data
@Document(collection = "orders")
public class OrderDocument {

    @Id
    private String id;

    private String userId;
    private String storeId;

    private List<OrderItem> items;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private Date createdAt;
}
