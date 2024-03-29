package pl.softwareplant.api.client.hystrix;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.softwareplant.api.client.SwapiClient;
import pl.softwareplant.api.client.dto.FilmDetailsDto;
import pl.softwareplant.api.client.dto.HomeWorldPlanetDto;
import pl.softwareplant.api.client.dto.PeopleDto;

@Slf4j
@Component
public class SwapiClientFallbackFactory implements FallbackFactory<SwapiClient> {


    @Override
    public SwapiClient create(Throwable cause) {
        log.error("Request to Swapi API has errors: {}", cause.toString());

        return new SwapiClient() {
            @Override
            public PeopleDto getPeopleFromPage(Integer pageNum, String characterPhrase) {
                log.error("Request to Swapi: getPeopleFromPage(), pageNum: {}, characterPhrase: {} - has errors: {}", pageNum, characterPhrase, cause.toString());
                return null;
            }

            @Override
            public HomeWorldPlanetDto getHomeWordById(Long homeWorldId, String homeWordSearchCriteria) {
                log.error("Request to Swapi: getHomeWordById(), homeWorldId: {}, homeWordSearchCriteria: {} - has errors: {}", homeWorldId, homeWordSearchCriteria, cause.toString());
                return null;
            }

            @Override
            public HomeWorldPlanetDto getPlanet(Long planetId) {
                log.error("Request to Swapi: getPlanet(), planetId: {} - has errors: {}", planetId, cause.toString());
                return null;
            }

            @Override
            public FilmDetailsDto getFilm(Long filmId) {
                log.error("Request to Swapi: getPlanet(), filmId: {} - has errors: {}", filmId, cause.toString());
                return null;
            }
        };
    }
}
