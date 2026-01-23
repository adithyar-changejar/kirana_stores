package com.example.kiranastore.service;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.kafka.ReportRequestProducer;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The type Report service.
 */
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportRequestProducer producer;

    /**
     * Request report string.
     *
     * @param userId the user id
     * @param from   the from
     * @param to     the to
     * @return the string
     */
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
                        .traceId(MDC.get("requestId"))   //TRACE PROPAGATION
                        .reportId(report.getId().toHexString())
                        .userId(userId)
                        .fromTime(from)
                        .toTime(to)
                        .build()
        );

        return report.getId().toHexString();
    }
}
