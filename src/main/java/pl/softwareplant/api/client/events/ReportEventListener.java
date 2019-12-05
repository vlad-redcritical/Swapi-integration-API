package pl.softwareplant.api.client.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.softwareplant.api.client.dto.CharacterDetailsResults;
import pl.softwareplant.api.client.service.IntegrationService;
import pl.softwareplant.api.service.ReportService;

import java.util.List;

@Slf4j
@Component
public class ReportEventListener implements ApplicationListener<ReportGenerationSpringEvent> {

    @Autowired
    IntegrationService integrationService;

    @Autowired
    ReportService reportService;

    @Override
    public void onApplicationEvent(ReportGenerationSpringEvent event) {
        log.info("Received report generation event: " + event.toString());
        List<CharacterDetailsResults> characterDetailsResultsList = integrationService.findPeopleByCriteria(event.getQueryCriteriaDto());
        reportService.createReport(characterDetailsResultsList, event.getQueryCriteriaDto(), event.getReport_id());
    }
}
