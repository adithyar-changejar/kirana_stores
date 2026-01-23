package com.example.kiranastore.dao;

import com.example.kiranastore.mongo.ReportDocument;
import com.example.kiranastore.repository.ReportRepository;
import org.springframework.stereotype.Repository;

/**
 * The type Report dao.
 */
@Repository
public class ReportDaoImpl implements ReportDao {

    private final ReportRepository reportRepository;

    /**
     * Instantiates a new Report dao.
     *
     * @param reportRepository the report repository
     */
    public ReportDaoImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportDocument save(ReportDocument report) {
        return reportRepository.save(report);
    }
}
