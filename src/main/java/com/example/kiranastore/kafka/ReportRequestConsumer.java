package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.service.ReportLifecycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportRequestConsumer {

    private final ReportLifecycleService lifecycleService;

    @KafkaListener(
            topics = "report_requests",
            groupId = "report-consumer-group"
    )
    public void consume(ReportRequestEvent event) {

        lifecycleService.generateReport(
                event.getRequestId(),
                event.getUserId(),
                event.getFromTime(),
                event.getToTime()
        );
    }
}
