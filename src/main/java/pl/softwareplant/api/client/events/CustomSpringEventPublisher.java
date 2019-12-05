package pl.softwareplant.api.client.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

@Slf4j
@Component
public class CustomSpringEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void generateReportAndPublishAnEvent(final Long report_id, final QueryCriteriaDto queryCriteriaDto) {
        log.info("Publishing custom event for report generation...");
        ReportGenerationSpringEvent customSpringEvent = new ReportGenerationSpringEvent(report_id, queryCriteriaDto);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
