package com.example.kiranastore.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "reports")
public class ReportDocument {

    @Id
    private String reportId;   // same as requestId

    private String userId;

    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    private double totalAmount;
    private int totalTransactions;

    private LocalDateTime generatedAt;
}
