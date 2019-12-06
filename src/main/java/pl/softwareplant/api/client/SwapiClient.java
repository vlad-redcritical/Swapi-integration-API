package pl.softwareplant.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.softwareplant.api.client.config.ClientConfig;
import pl.softwareplant.api.client.dto.FilmDetailsDto;
import pl.softwareplant.api.client.dto.HomeWorldPlanetDto;
import pl.softwareplant.api.client.dto.PeopleDto;

/*TODO Provide error decoder, circuit breaker and hystrix fallback */
@FeignClient(value = "swapiclient", url = "https://swapi.co/api", configuration = ClientConfig.class)
public interface SwapiClient {

    @GetMapping(value = "/people/?page={pageNum}&search={characterPhrase}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    PeopleDto getPeopleFromPage(@PathVariable Integer pageNum, @PathVariable String characterPhrase);

    @GetMapping(value = "/planets/{homeWorldId}/?search={homeWordSearchCriteria}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    HomeWorldPlanetDto getHomeWordById(@PathVariable Long homeWorldId, @PathVariable String homeWordSearchCriteria);

    @GetMapping(value = "/planets/{planetId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    HomeWorldPlanetDto getPlanet(@PathVariable Long planetId);

    @GetMapping(value = "/films/{filmId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FilmDetailsDto getFilm(@PathVariable Long filmId);
}
