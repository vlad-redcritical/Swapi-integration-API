package pl.softwareplant.api.web.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.softwareplant.api.client.events.CustomSpringEventPublisher;
import pl.softwareplant.api.persistence.entity.Report;
import pl.softwareplant.api.service.ReportService;
import pl.softwareplant.api.web.controller.ApiController;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
public class ApiControllerImpl implements ApiController {

    @Autowired
    CustomSpringEventPublisher customSpringEventPublisher;

    @Autowired
    ReportService reportService;

    @Override
    public ResponseEntity putReport(Long report_id, QueryCriteriaDto queryCriteriaDto) {
        log.info("Registered PUT request with criteria: {}", queryCriteriaDto.toString());
        customSpringEventPublisher.generateReportAndPublishAnEvent(report_id, queryCriteriaDto);
        return new ResponseEntity(NO_CONTENT);
    }

    @Override
    public ResponseEntity getAllReports() {
        log.info("Registered GET request");
        List<Report> reportList = reportService.getAllReports();
        if (!reportList.isEmpty())
            return new ResponseEntity<>(reportList, HttpStatus.OK);
        return new ResponseEntity<>(NOT_FOUND);
    }

    @Override
    public ResponseEntity getReport(Long report_id) {
        log.info("Registered GET request with criteria: {}", report_id);
        Optional<Report> report = reportService.getReportById(report_id);
        return report.<ResponseEntity>map(reportOut -> new ResponseEntity<>(reportOut, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @Override
    public ResponseEntity deleteReport(Long report_id) {
        log.info("Registered DELETE request with criteria: {}", report_id);
        reportService.deleteReportById(report_id);
        return new ResponseEntity(NO_CONTENT);
    }

    @Override
    public ResponseEntity deleteAllReports() {
        log.info("Registered DELETE request");
        reportService.deleteAllReports();
        return new ResponseEntity(NO_CONTENT);
    }
}
