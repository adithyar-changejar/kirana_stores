package com.example.kiranastore.controller;

import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final ReportRepository reportRepository;

    public AdminReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ReportDocument> getAllReports() {
        return reportRepository.findAll();
    }
}
