package pl.softwareplant.api.web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import javax.validation.Valid;

@RequestMapping(value = "/report")
public interface ApiController {

    @PutMapping(value = "/{report_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity putReport(@PathVariable String report_id, @Valid @RequestBody QueryCriteriaDto queryCriteriaDto);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getAllReports();

    @GetMapping(value = "/{report_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getReport(@PathVariable String report_id);

    @DeleteMapping(value = "/{report_id}")
    ResponseEntity deleteReport(@PathVariable String report_id);

    @DeleteMapping
    ResponseEntity deleteAllReports();
}
