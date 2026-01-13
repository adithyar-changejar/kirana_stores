package com.example.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestEvent {

    private String userId;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private String requestId;
}
