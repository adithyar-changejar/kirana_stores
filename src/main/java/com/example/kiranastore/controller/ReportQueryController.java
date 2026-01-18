package com.example.kiranastore.controller;

import com.example.kiranastore.dto.ReportResponseDTO;
import com.example.kiranastore.service.TransactionReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportQueryController {

    private final TransactionReportService transactionReportService;

    public ReportQueryController(
            TransactionReportService transactionReportService
    ) {
        this.transactionReportService = transactionReportService;
    }

    @GetMapping("/{reportId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReportResponseDTO> getReport(
            @PathVariable String reportId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                transactionReportService.getReportByIdForUser(
                        reportId,
                        authentication.getName()
                )
        );
    }
}
