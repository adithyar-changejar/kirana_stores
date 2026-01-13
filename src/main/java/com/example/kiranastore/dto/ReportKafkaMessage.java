package com.example.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportKafkaMessage {

    private Long userId;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private String requestId;
}
