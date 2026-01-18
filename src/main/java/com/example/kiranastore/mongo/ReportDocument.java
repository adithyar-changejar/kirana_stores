package com.example.kiranastore.mongo;

import com.example.kiranastore.entity.ReportStatus;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reports")
public class ReportDocument {

    @Id
    private ObjectId id;

    private String userId; // Mongo ObjectId stored as HEX STRING

    private Date fromTime;
    private Date toTime;

    private double totalCredits;
    private double totalDebits;
    private double netAmount;
    private int totalTransactions;

    private ReportStatus status;

    private Date createdAt;
    private Date updatedAt;
}
