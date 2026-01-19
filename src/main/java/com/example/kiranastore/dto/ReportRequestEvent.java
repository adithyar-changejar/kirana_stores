package com.example.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder                //
@NoArgsConstructor      //
@AllArgsConstructor
public class ReportRequestEvent {

    private String requestId;
    private String userId;
    private Date fromTime;
    private Date toTime;
}
