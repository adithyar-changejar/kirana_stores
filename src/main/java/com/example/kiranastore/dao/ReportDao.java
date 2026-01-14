package com.example.kiranastore.dao;

import com.example.kiranastore.mongo.ReportDocument;

public interface ReportDao {
    ReportDocument save(ReportDocument report);
}
