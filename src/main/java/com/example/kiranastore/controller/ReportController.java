package com.example.kiranastore.controller;

import com.example.kiranastore.dto.ReportRequestDTO;
import com.example.kiranastore.dto.ReportRequestResponseDTO;
import com.example.kiranastore.entity.ReportStatus;
import com.example.kiranastore.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * The type Report controller.
 */
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    /**
     * Instantiates a new Report controller.
     *
     * @param reportService the report service
     */
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Request report response entity.
     *
     * @param request        the request
     * @param authentication the authentication
     * @return the response entity
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ReportRequestResponseDTO> requestReport(
            @RequestBody ReportRequestDTO request,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        String reportId = reportService.requestReport(
                userId,
                request.getFromTime(),
                request.getToTime()
        );

        return ResponseEntity.ok(
                new ReportRequestResponseDTO(
                        reportId,
                        ReportStatus.REQUESTED
                )
        );
    }
}
