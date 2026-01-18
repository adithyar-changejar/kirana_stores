package com.example.kiranastore.dto;

import com.example.kiranastore.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportRequestResponseDTO {

    private String reportId;
    private ReportStatus status;
}
