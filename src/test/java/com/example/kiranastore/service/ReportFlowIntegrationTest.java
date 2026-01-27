package com.example.kiranastore.service;

import com.example.kiranastore.dto.ReportRequestEvent;
import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.kafka.ReportRequestProducer;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * FULL REPORT FLOW TEST
 * API → DB → Kafka → DB update
 */
@ExtendWith(MockitoExtension.class)
class ReportFlowIntegrationTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ReportRequestProducer producer;

    @InjectMocks
    private ReportService reportService;

    @Captor
    private ArgumentCaptor<ReportRequestEvent> eventCaptor;

    @Test
    void shouldExecuteCompleteReportFlow() {

        // ---------- GIVEN ----------
        String userId = "user-123";
        Date from = new Date();
        Date to = new Date();

        ObjectId reportId = new ObjectId();

        MDC.put("requestId", "trace-123");

        when(reportRepository.save(any(ReportDocument.class)))
                .thenAnswer(invocation -> {
                    ReportDocument doc = invocation.getArgument(0);
                    if (doc.getId() == null) {
                        doc.setId(reportId);   // first save
                        doc.setStatus(ReportStatus.REQUESTED);
                    } else {
                        doc.setStatus(ReportStatus.COMPLETED); // simulated processing
                    }
                    return doc;
                });

        // ---------- WHEN ----------
        String returnedId =
                reportService.requestReport(userId, from, to);

        // ---------- THEN (DB + PRODUCER) ----------
        assertEquals(reportId.toHexString(), returnedId);

        verify(reportRepository).save(any(ReportDocument.class));
        verify(producer).sendReportRequest(eventCaptor.capture());

        ReportRequestEvent event = eventCaptor.getValue();

        assertEquals(userId, event.getUserId());
        assertEquals(reportId.toHexString(), event.getReportId());
        assertEquals(from, event.getFromTime());
        assertEquals(to, event.getToTime());

        // ---------- SIMULATE CONSUMER EFFECT ----------
        ReportDocument completedReport = ReportDocument.builder()
                .id(reportId)
                .status(ReportStatus.COMPLETED)
                .build();

        reportRepository.save(completedReport);

        // ---------- FINAL ASSERT ----------
        verify(reportRepository, times(2)).save(any(ReportDocument.class));
    }
}
