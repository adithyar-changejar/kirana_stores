package com.example.kiranastore.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestEvent {

    //  TRACE ID (for logs / Kibana)
    private String traceId;

    private String reportId;

    private String userId;
    private Date fromTime;
    private Date toTime;
}
