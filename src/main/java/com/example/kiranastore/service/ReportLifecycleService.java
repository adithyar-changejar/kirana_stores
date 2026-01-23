package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The type Report lifecycle service.
 */
@Service
@RequiredArgsConstructor
public class ReportLifecycleService {

    private final ReportRepository reportRepository;
    private final TransactionDao transactionDao;

    /**
     * Generate report.
     *
     * @param reportId the report id
     * @param userId   the user id
     * @param from     the from
     * @param to       the to
     */
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

        double totalCredits =
                transactionDao.getTotalCredits(userId, from, to);

        double totalDebits =
                transactionDao.getTotalDebits(userId, from, to);

        long totalTransactions =
                transactionDao.getTransactionCount(userId, from, to);

        report.setTotalCredits(totalCredits);
        report.setTotalDebits(totalDebits);
        report.setNetAmount(totalCredits - totalDebits);
        report.setTotalTransactions((int) totalTransactions);
        report.setStatus(ReportStatus.COMPLETED);
        report.setUpdatedAt(new Date());

        reportRepository.save(report);
    }
}
