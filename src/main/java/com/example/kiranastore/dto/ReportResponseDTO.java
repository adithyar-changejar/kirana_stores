package com.example.kiranastore.dto;

import com.example.kiranastore.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor        //
@AllArgsConstructor
public class ReportResponseDTO {

    private String reportId;
    private Date fromTime;
    private Date toTime;
    private double totalCredits;
    private double totalDebits;
    private double netAmount;
    private int totalTransactions;
    private ReportStatus status;
}
