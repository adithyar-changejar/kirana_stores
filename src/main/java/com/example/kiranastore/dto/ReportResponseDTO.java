package com.example.kiranastore.dto;

import com.example.kiranastore.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {

    private String requestId;
    private ReportStatus status;
}
