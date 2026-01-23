package com.example.kiranastore.dto;

import com.example.kiranastore.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Report request response dto.
 */
@Data
@AllArgsConstructor
public class ReportRequestResponseDTO {

    private String reportId;
    private ReportStatus status;
}
