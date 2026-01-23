package com.example.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The type Report kafka message.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportKafkaMessage {

    private String userId;
    private Date fromTime;
    private Date toTime;
    private String requestId;
}
