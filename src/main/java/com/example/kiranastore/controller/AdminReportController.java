package com.example.kiranastore.controller;

import com.example.kiranastore.dto.ReportResponseDTO;
import com.example.kiranastore.service.TransactionReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final TransactionReportService reportService;

    public AdminReportController(TransactionReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponseDTO> getReport(
            @PathVariable String reportId
    ) {
        return ResponseEntity.ok(
                reportService.getReportForAdmin(reportId)
        );
    }
}
