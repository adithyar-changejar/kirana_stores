package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class TransactionReportService {

    private final TransactionDao transactionDao;
    private final ReportLifecycleService lifecycleService;
    private final TransactionAggregationService aggregationService;
    private final ReportRepository reportRepository;

    public TransactionReportService(
            TransactionDao transactionDao,
            ReportLifecycleService lifecycleService,
            TransactionAggregationService aggregationService,
            ReportRepository reportRepository
    ) {
        this.transactionDao = transactionDao;
        this.lifecycleService = lifecycleService;
        this.aggregationService = aggregationService;
        this.reportRepository = reportRepository;
    }


    @Cacheable(value = "reports", key = "#requestId")
    public ReportDocument getReportByRequestId(String requestId) {


        System.out.println("FETCHING REPORT FROM MONGODB");

        return reportRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public void generateTransactionReport(
            String userId,
            Date from,
            Date to,
            String requestId
    ) {

        ReportDocument report = lifecycleService.markInProgress(requestId);

        try {
            Date normalizedFrom = normalizeStart(from);
            Date normalizedTo = normalizeEnd(to);

            var transactions =
                    transactionDao.findByUserIdAndCreatedAtBetween(
                            userId,
                            normalizedFrom,
                            normalizedTo
                    );

            var result = aggregationService.aggregate(transactions);

            report.setFromTime(normalizedFrom);
            report.setToTime(normalizedTo);

            lifecycleService.markCompleted(
                    report,
                    result.totalCredits(),
                    result.totalDebits(),
                    result.netAmount(),
                    result.totalTransactions()
            );

        } catch (Exception e) {
            lifecycleService.markFailed(report);
            throw e;
        }
    }

    private Date normalizeStart(Date date) {
        return Date.from(
                date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    private Date normalizeEnd(Date date) {
        return Date.from(
                date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .atTime(23, 59, 59)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }
}
