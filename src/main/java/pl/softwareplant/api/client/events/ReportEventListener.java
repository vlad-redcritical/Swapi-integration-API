package pl.softwareplant.api.client.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.softwareplant.api.client.service.IntegrationService;

@Slf4j
@Component
public class ReportEventListener implements ApplicationListener<ReportGenerationSpringEvent> {
    @Autowired
    IntegrationService integrationService;

    @Override
    public void onApplicationEvent(ReportGenerationSpringEvent reportGenerationSpringEvent) {
        log.info("Received report generation event: " + reportGenerationSpringEvent.toString());

        integrationService.findPeopleByCriteria(reportGenerationSpringEvent.getQueryCriteriaDto());

    }
}
