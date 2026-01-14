package com.example.kiranastore.service;

import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReportLifecycleService {

    private final ReportRepository reportRepository;

    public ReportLifecycleService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportDocument markInProgress(String requestId) {
        ReportDocument report = reportRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setStatus(ReportStatus.IN_PROGRESS);
        reportRepository.save(report);
        return report;
    }

    public void markCompleted(
            ReportDocument report,
            double totalCredits,
            double totalDebits,
            double netAmount,
            int totalTransactions
    ) {
        report.setTotalCredits(totalCredits);
        report.setTotalDebits(totalDebits);
        report.setNetAmount(netAmount);
        report.setTotalTransactions(totalTransactions);
        report.setStatus(ReportStatus.COMPLETED);
        report.setGeneratedAt(new Date());

        reportRepository.save(report);
    }

    public void markFailed(ReportDocument report) {
        report.setStatus(ReportStatus.FAILED);
        reportRepository.save(report);
    }
}
