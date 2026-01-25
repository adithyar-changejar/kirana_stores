package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Report request producer (COMMAND → Kafka)
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
     * Send report request (ASYNC command)
     *
     * @param event the event
     */
    public void sendReportRequest(ReportRequestEvent event) {

        // Put traceId in MDC for logs
        if (event.getTraceId() != null) {
            MDC.put("requestId", event.getTraceId());
        }

        ProducerRecord<String, ReportRequestEvent> record =
                new ProducerRecord<>(TOPIC, event.getReportId(), event);

        // Attach traceId to Kafka headers
        if (event.getTraceId() != null) {
            record.headers().add("traceId", event.getTraceId().getBytes());
        }

        log.info(" Sending report request to Kafka | reportId={} userId={}",
                event.getReportId(), event.getUserId());

        // ASYNC send with callback (IMPORTANT FOR LOGS)
        kafkaTemplate.send(record)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error(" Kafka producer FAILED | reportId={}", event.getReportId(), ex);
                    } else {
                        log.info(
                                " Kafka producer SENT | topic={} partition={} offset={} reportId={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset(),
                                event.getReportId()
                        );
                    }
                });

        MDC.clear();
    }
}
