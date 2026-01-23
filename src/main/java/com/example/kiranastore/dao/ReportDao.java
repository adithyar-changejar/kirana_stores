package com.example.kiranastore.dao;

import com.example.kiranastore.mongo.ReportDocument;

/**
 * The interface Report dao.
 */
public interface ReportDao {
    /**
     * Save report document.
     *
     * @param report the report
     * @return the report document
     */
    ReportDocument save(ReportDocument report);
}
