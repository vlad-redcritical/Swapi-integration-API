package pl.softwareplant.api.client.service;

import pl.softwareplant.api.client.dto.CharacterDetailsResults;
import pl.softwareplant.api.client.dto.FilmDetailsDto;
import pl.softwareplant.api.client.dto.HomeWorldPlanetDto;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.List;

public interface IntegrationService {
    List<CharacterDetailsResults> findPeopleByCriteria(final QueryCriteriaDto queryCriteriaDto);

    HomeWorldPlanetDto findRequiredHomeWord(final String homeWordUrl, final String homeWordSearchCriteria);

    HomeWorldPlanetDto findPlanet(final String homeWordUrl);

    FilmDetailsDto findFilm(final String filmUrl);
}
