package com.example.kiranastore.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reports")
@Data
public class ReportDocument {

    @Id
    private String reportId;

    private Long userId;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    private double totalAmount;
    private int totalTransactions;

    private LocalDateTime generatedAt;


}
