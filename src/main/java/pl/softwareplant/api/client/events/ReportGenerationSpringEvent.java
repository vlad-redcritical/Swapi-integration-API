package pl.softwareplant.api.client.events;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

@Getter
@ToString
public class ReportGenerationSpringEvent extends ApplicationEvent {
    private String report_id;

    private QueryCriteriaDto queryCriteriaDto;

    ReportGenerationSpringEvent(String report_id, QueryCriteriaDto queryCriteriaDto) {
        super("");
        this.report_id = report_id;
        this.queryCriteriaDto = queryCriteriaDto;
    }
}
