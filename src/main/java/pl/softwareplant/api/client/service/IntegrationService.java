package pl.softwareplant.api.client.service;

import pl.softwareplant.api.client.dto.HomeWorldPlanetDto;
import pl.softwareplant.api.client.dto.PeopleDto;
import pl.softwareplant.api.client.dto.FilmDetailsDto;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

public interface IntegrationService {
    PeopleDto findPeopleByCriteria(final QueryCriteriaDto queryCriteriaDto);

    HomeWorldPlanetDto findRequiredHomeWord(final String homeWordUrl, final String homeWordSearchCriteria);

    FilmDetailsDto findFilm(final String filmUrl);
}
