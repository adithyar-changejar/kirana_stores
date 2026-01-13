package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportRequestProducer {

    private static final String TOPIC = "report_requests";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ReportRequestProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //TODO could create kafka service as generic
    public void sendReportRequest(ReportRequestEvent event) {
        kafkaTemplate.send(TOPIC, event.getRequestId(), event);
    }
}
