package com.example.kiranastore.controller;

import com.example.kiranastore.dto.ReportRequestDTO;
import com.example.kiranastore.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {


    /*
- Report API entry
- Accept report request
- Trigger async flow
- Return request id
*/
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> requestReport(
            @RequestBody ReportRequestDTO request
    ) {

        String requestId = reportService.requestReport(
                request.getUserId(),
                request.getFromTime(),
                request.getToTime()
        );
        //TODO get response from service itself

        return ResponseEntity.ok(
                Map.of(
                        "requestId", requestId,
                        "status", "REQUEST_ACCEPTED"
                )
        );
    }
}
