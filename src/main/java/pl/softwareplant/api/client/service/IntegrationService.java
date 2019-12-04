package pl.softwareplant.api.client.service;

import pl.softwareplant.api.client.dto.HomeWorldDto;
import pl.softwareplant.api.client.dto.PeopleDto;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

public interface IntegrationService {
    PeopleDto findPeopleByCriteria(final QueryCriteriaDto queryCriteriaDto);

    HomeWorldDto findRequiredHomeWord(final String homeWordUrl, final String homeWordSearchCriteria);
}
