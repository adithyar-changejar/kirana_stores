package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.mongo.ReportDocument;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class TransactionReportService {

    private final TransactionDao transactionDao;
    private final ReportLifecycleService lifecycleService;
    private final TransactionAggregationService aggregationService;

    public TransactionReportService(
            TransactionDao transactionDao,
            ReportLifecycleService lifecycleService,
            TransactionAggregationService aggregationService
    ) {
        this.transactionDao = transactionDao;
        this.lifecycleService = lifecycleService;
        this.aggregationService = aggregationService;
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
