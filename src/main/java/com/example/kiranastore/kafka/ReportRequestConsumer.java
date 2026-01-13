package com.example.kiranastore.kafka;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.service.TransactionReportService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReportRequestConsumer {

    /*
- Kafka consumer
- Receive report events
- Trigger report generation
- Async processing
*/


    private final TransactionReportService transactionReportService;

    public ReportRequestConsumer(TransactionReportService transactionReportService) {
        this.transactionReportService = transactionReportService;
    }

    @KafkaListener(
            topics = "report_requests",
            groupId = "report-consumer-group"
    )
    public void consumeReportRequest(ReportRequestEvent event) {

        System.out.println("📥 Kafka message received: " + event);

        transactionReportService.generateTransactionReport(
                event.getUserId(),
                event.getFromTime(),
                event.getToTime(),
                event.getRequestId()
        );
    }
}
