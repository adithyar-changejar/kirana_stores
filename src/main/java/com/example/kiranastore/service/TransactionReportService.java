package com.example.kiranastore.service;

import com.example.kiranastore.dto.ReportResponseDTO;
import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * The type Transaction report service.
 */
@Service
public class TransactionReportService {

    private final ReportRepository reportRepository;

    /**
     * Instantiates a new Transaction report service.
     *
     * @param reportRepository the report repository
     */
    public TransactionReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Gets report by id for user.
     *
     * @param reportId the report id
     * @param userId   the user id
     * @return the report by id for user
     */
    /*USER ACCESS (userId enforced)*/
    @Cacheable(value = "reports", key = "#reportId")
    public ReportResponseDTO getReportByIdForUser(
            String reportId,
            String userId
    ) {
        System.out.println(" USER CACHE MISS → MongoDB | reportId=" + reportId);

        ObjectId objectId = new ObjectId(reportId);

        ReportDocument report = reportRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (!report.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        return map(report);
    }

    /**
     * Gets report for admin.
     *
     * @param reportId the report id
     * @return the report for admin
     */
    /*ADMIN / SUPER_ADMIN ACCESS (NO userId check)*/
    public ReportResponseDTO getReportForAdmin(String reportId) {

        ObjectId objectId;
        try {
            objectId = new ObjectId(reportId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid reportId");
        }

        ReportDocument report = reportRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        return map(report);
    }

    private ReportResponseDTO map(ReportDocument report) {
        return new ReportResponseDTO(
                report.getId().toHexString(),
                report.getFromTime(),
                report.getToTime(),
                report.getTotalCredits(),
                report.getTotalDebits(),
                report.getNetAmount(),
                report.getTotalTransactions(),
                report.getStatus()
        );
    }
}
