package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.service.ReportLifecycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Report request consumer (Kafka → async processing)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportRequestConsumer {

    private final ReportLifecycleService lifecycleService;

    /**
     * Consume report request command
     */
    @KafkaListener(
            topics = "report_requests",
            groupId = "report-consumer-group"
    )
    public void consume(ReportRequestEvent event) {

        // Restore traceId for logs
        if (event.getTraceId() != null) {
            MDC.put("requestId", event.getTraceId());
        }

        try {
            log.info(
                    " Kafka consumer RECEIVED | reportId={} userId={} from={} to={}",
                    event.getReportId(),
                    event.getUserId(),
                    event.getFromTime(),
                    event.getToTime()
            );

            lifecycleService.generateReport(
                    event.getReportId(),
                    event.getUserId(),
                    event.getFromTime(),
                    event.getToTime()
            );

            log.info(
                    " Kafka consumer COMPLETED report generation | reportId={}",
                    event.getReportId()
            );

        } catch (Exception e) {
            log.error(
                    " Kafka consumer FAILED | reportId={}",
                    event.getReportId(),
                    e
            );
            throw e;
        } finally {
            MDC.clear();
        }
    }
}
