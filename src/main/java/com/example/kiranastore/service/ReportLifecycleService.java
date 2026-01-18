package com.example.kiranastore.service;

import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReportLifecycleService {

    private final ReportRepository reportRepository;

    public void generateReport(
            String reportId,
            String userId,
            Date from,
            Date to
    ) {

        ReportDocument report = reportRepository
                .findById(new ObjectId(reportId))
                .orElseThrow();

        report.setStatus(ReportStatus.IN_PROGRESS);
        report.setUpdatedAt(new Date());
        reportRepository.save(report);

        // fake aggregation
        report.setTotalCredits(1000);
        report.setTotalDebits(200);
        report.setNetAmount(800);
        report.setTotalTransactions(3);
        report.setStatus(ReportStatus.COMPLETED);
        report.setUpdatedAt(new Date());

        reportRepository.save(report);
    }
}
