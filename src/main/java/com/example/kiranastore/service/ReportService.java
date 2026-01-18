package com.example.kiranastore.service;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.kafka.ReportRequestProducer;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportRequestProducer producer;

    public String requestReport(String userId, Date from, Date to) {

        ReportDocument report = ReportDocument.builder()
                .userId(userId)
                .fromTime(from)
                .toTime(to)
                .status(ReportStatus.REQUESTED)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        report = reportRepository.save(report);

        producer.sendReportRequest(
                ReportRequestEvent.builder()
                        .requestId(report.getId().toHexString())
                        .userId(userId)
                        .fromTime(from)
                        .toTime(to)
                        .build()
        );

        return report.getId().toHexString();
    }
}
