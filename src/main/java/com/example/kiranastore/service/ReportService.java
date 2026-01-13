package com.example.kiranastore.service;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.kafka.ReportRequestProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReportService {

    private final ReportRequestProducer producer;
    private final TransactionReportService transactionReportService;

    public ReportService(
            ReportRequestProducer producer,
            TransactionReportService transactionReportService
    ) {
        this.producer = producer;
        this.transactionReportService = transactionReportService;
    }

    public String requestReport(String userId, LocalDateTime from, LocalDateTime to) {

        String requestId = UUID.randomUUID().toString();

        ReportRequestEvent event = new ReportRequestEvent();
        event.setRequestId(requestId);
        event.setUserId(userId);
        event.setFromTime(from);
        event.setToTime(to);

        producer.sendReportRequest(event);

        return requestId;
    }
}
