package com.example.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestEvent {

    private String userId;
    private Date fromTime;
    private Date toTime;
    private String requestId;
}
