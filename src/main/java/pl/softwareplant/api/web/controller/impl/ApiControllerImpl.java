package pl.softwareplant.api.web.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.softwareplant.api.web.controller.ApiController;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

@Slf4j
@RestController
public class ApiControllerImpl implements ApiController {

    @Override
    public ResponseEntity putReport(String report_id, QueryCriteriaDto queryCriteriaDto) {
        log.info("Registered PUT request with criteria: {}", queryCriteriaDto.toString());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity getAllReports() {
        log.info("Registered GET request");

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity getReport(String report_id) {
        log.info("Registered GET request with criteria: {}", report_id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteReport(String report_id) {
        log.info("Registered DELETE request with criteria: {}", report_id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity deleteAllReports() {
        log.info("Registered DELETE request");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
