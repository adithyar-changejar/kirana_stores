package com.example.kiranastore.controller;

import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.service.TransactionReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportQueryController {

    private final TransactionReportService reportService;

    public ReportQueryController(TransactionReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{requestId}")
    public ResponseEntity<ReportDocument> getReport(
            @PathVariable String requestId
    ) {


        return ResponseEntity.ok(
                reportService.getReportByRequestId(requestId)
        );
    }
}
