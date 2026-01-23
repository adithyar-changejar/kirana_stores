package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * The type Report request producer.
 */
@Slf4j
@Component
public class ReportRequestProducer {

    private static final String TOPIC = "report_requests";

    private final KafkaTemplate<String, ReportRequestEvent> kafkaTemplate;

    /**
     * Instantiates a new Report request producer.
     *
     * @param kafkaTemplate the kafka template
     */
    public ReportRequestProducer(KafkaTemplate<String, ReportRequestEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send report request.
     *
     * @param event the event
     */
    public void sendReportRequest(ReportRequestEvent event) {

        ProducerRecord<String, ReportRequestEvent> record =
                new ProducerRecord<>(TOPIC, event.getReportId(), event);

        //  Attach traceId to Kafka headers
        if (event.getTraceId() != null) {
            record.headers().add("traceId", event.getTraceId().getBytes());
        }

        kafkaTemplate.send(record);

        log.info(" Sent report request to Kafka | reportId={}", event.getReportId());
    }
}
