package com.example.kiranastore.service;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.kafka.ReportRequestProducer;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ReportService {

    private final ReportRequestProducer producer;
    private final ReportRepository reportRepository;

    public ReportService(
            ReportRequestProducer producer,
            ReportRepository reportRepository
    ) {
        this.producer = producer;
        this.reportRepository = reportRepository;
    }

    public String requestReport(String userId, Date from, Date to) {

        String requestId = UUID.randomUUID().toString();

        //  SAVE REPORT WITH REQUESTED STATUS
        ReportDocument report = new ReportDocument();
        report.setReportId(requestId);
        report.setUserId(userId);
        report.setFromTime(from);
        report.setToTime(to);
        report.setStatus(ReportStatus.REQUESTED);
        report.setGeneratedAt(new Date());

        reportRepository.save(report);

        //  SEND KAFKA EVENT
        ReportRequestEvent event = new ReportRequestEvent(
                userId,
                from,
                to,
                requestId
        );

        producer.sendReportRequest(event);

        return requestId;
    }
}
