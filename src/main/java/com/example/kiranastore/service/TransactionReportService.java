package com.example.kiranastore.service;

import com.example.kiranastore.dao.TransactionDao;
import com.example.kiranastore.entity.TransactionEntity;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionReportService {

    private final TransactionDao transactionDao;
    private final ReportRepository reportRepository;

    public TransactionReportService(
            TransactionDao transactionDao,
            ReportRepository reportRepository
    ) {
        this.transactionDao = transactionDao;
        this.reportRepository = reportRepository;
    }

    public void generateTransactionReport(
            String userId,
            LocalDateTime from,
            LocalDateTime to,
            String requestId
    ) {

        List<TransactionEntity> transactions =
                transactionDao.findByUserIdAndCreatedAtBetween(
                        userId, from, to
                );

        double totalAmount = transactions.stream()
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        ReportDocument report = new ReportDocument();
        report.setReportId(requestId);
        report.setUserId(userId);
        report.setFromTime(from);
        report.setToTime(to);
        report.setTotalAmount(totalAmount);
        report.setTotalTransactions(transactions.size());
        report.setGeneratedAt(LocalDateTime.now());

        //TODO Dao for this
        reportRepository.save(report);

        System.out.println(
                " Report generated and stored with requestId = " + requestId
        );
    }
}
