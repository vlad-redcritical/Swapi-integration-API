package pl.softwareplant.api.client.service;

import pl.softwareplant.api.client.dto.PeopleDto;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

public interface IntegrationService {
    PeopleDto findPeopleByCriteria(final QueryCriteriaDto queryCriteriaDto);
}
