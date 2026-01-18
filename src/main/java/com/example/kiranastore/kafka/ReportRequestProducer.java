package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportRequestProducer {

    private static final String TOPIC = "report_requests";

    private final KafkaTemplate<String, ReportRequestEvent> kafkaTemplate;

    public ReportRequestProducer(
            KafkaTemplate<String, ReportRequestEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReportRequest(ReportRequestEvent event) {
        kafkaTemplate.send(TOPIC, event.getRequestId(), event);
        System.out.println("📨 Sent report request to Kafka | " + event);
    }
}
