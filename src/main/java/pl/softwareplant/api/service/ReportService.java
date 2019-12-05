package pl.softwareplant.api.service;

import pl.softwareplant.api.client.dto.CharacterDetailsResults;
import pl.softwareplant.api.persistence.entity.Report;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Optional<Report> getReportById(final Long reportId);

    List<Report> getAllReports();

    void deleteReportById(final Long reportId);

    void deleteAllReports();

    void createReport(List<CharacterDetailsResults> resultsList, QueryCriteriaDto queryCriteria, Long reportId);

}
