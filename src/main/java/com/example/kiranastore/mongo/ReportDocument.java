package com.example.kiranastore.mongo;

import com.example.kiranastore.entity.ReportStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "reports")
public class ReportDocument {

    @Id
    private String reportId;

    private String userId;

    private Date fromTime;
    private Date toTime;

    private double totalCredits;
    private double totalDebits;
    private double netAmount;

    private int totalTransactions;

    private ReportStatus status;

    private Date generatedAt;
}
