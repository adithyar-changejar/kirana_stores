package com.example.kiranastore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReportRequestDTO {

    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fromTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date toTime;
}
