package com.example.kiranastore.controller;

import com.example.kiranastore.dto.ReportResponseDTO;
import com.example.kiranastore.service.TransactionReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type Admin report controller.
 */
@RestController
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final TransactionReportService reportService;

    /**
     * Instantiates a new Admin report controller.
     *
     * @param reportService the report service
     */
    public AdminReportController(TransactionReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Gets report.
     *
     * @param reportId the report id
     * @return the report
     */
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
