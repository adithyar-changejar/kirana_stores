package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.service.ReportLifecycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * The type Report request consumer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportRequestConsumer {

    private final ReportLifecycleService lifecycleService;

    /**
     * Consume.
     *
     * @param event the event
     */
    @KafkaListener(
            topics = "report_requests",
            groupId = "report-consumer-group"
    )
    public void consume(ReportRequestEvent event) {


        MDC.put("requestId", event.getTraceId());

        try {
            log.info("Kafka consumer processing report request");

            lifecycleService.generateReport(
                    event.getReportId(),
                    event.getUserId(),
                    event.getFromTime(),
                    event.getToTime()
            );

            log.info("Kafka consumer completed report generation");

        } finally {
            MDC.clear();
        }
    }
}
