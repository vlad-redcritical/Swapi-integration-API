package pl.softwareplant.api.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import pl.softwareplant.api.client.SwapiClient;
import pl.softwareplant.api.client.dto.CharacterDetails;
import pl.softwareplant.api.client.dto.FilmDetailsDto;
import pl.softwareplant.api.client.dto.HomeWorldPlanetDto;
import pl.softwareplant.api.client.dto.PeopleDto;
import pl.softwareplant.api.client.service.IntegrationService;
import pl.softwareplant.api.client.service.impl.IntegrationServiceImpl;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class IntegrationServiceTests {

    @InjectMocks
    IntegrationService integrationService = new IntegrationServiceImpl();

    @Mock
    SwapiClient swapiClient;

    Integer clientDefaultStartPage = 1;

    Integer clientDefaultPerPage = 10;

    @Mock
    Cache<String, String> planetHomeWordCache;

    @Mock
    Cache<String, String> filmsDetailsCache;

    @BeforeEach
    void setMockOutput() throws Exception {
        /* Mock client service: getPeopleFromPage*/
        var characterDetailsMock = CharacterDetails.builder().name("Joda").homeworld("https://swapi.co/api/planets/36/")
                .films(List.of("https://swapi.co/api/films/4/", "https://swapi.co/api/films/5/", "https://swapi.co/api/films/6/")).url("https://swapi.co/api/people/44/").build();

        var characterDetailsMock2 = CharacterDetails.builder().name("Nie Joda").homeworld("https://swapi.co/api/planets/37/")
                .films(List.of("https://swapi.co/api/films/5/", "https://swapi.co/api/films/6/", "https://swapi.co/api/films/7/")).url("https://swapi.co/api/people/45/").build();

        var peopleDtoMock = PeopleDto.builder().count(24).next("https://swapi.co/api/people/?search=J&page=2&format=json").results(List.of(characterDetailsMock, characterDetailsMock2)).previous(null).build();
        lenient().when(swapiClient.getPeopleFromPage(anyInt(), anyString())).thenReturn(peopleDtoMock);


        /* Mock client service: getHomeWordById*/
        var mockHomeWorldPlanetDto = HomeWorldPlanetDto.builder().name("Tatooine").build();
        lenient().when(swapiClient.getHomeWordById(anyLong(), anyString())).thenReturn(mockHomeWorldPlanetDto);

        /* Mock client service: getFilm*/
        var mockFilmDetailsDto = FilmDetailsDto.builder().title("Second Part II").build();
        lenient().when(swapiClient.getFilm(anyLong())).thenReturn(mockFilmDetailsDto);

        /* Mock client service: getPlanet*/
        lenient().when(swapiClient.getPlanet(anyLong())).thenReturn(mockHomeWorldPlanetDto);



        /*
         * Reflection spring-test:  initialization of private fields with annotation @Value
         * */
        ReflectionTestUtils.setField(integrationService, "clientDefaultStartPage", clientDefaultStartPage, Integer.class);
        ReflectionTestUtils.setField(integrationService, "clientDefaultPerPage", clientDefaultPerPage, Integer.class);
    }

    @Test
    void should_findFilm() {
        var findFilmResult = integrationService.findFilm("https://swapi.co/api/films/1/");
        Assertions.assertNotNull(findFilmResult);
        Assertions.assertEquals("Second Part II", findFilmResult.getTitle());

        var findFilmResultNull = integrationService.findFilm(null);
        Assertions.assertNull(findFilmResultNull);
    }

    @Test
    void should_findPlanet() {
        var findPlanetResult = integrationService.findPlanet("https://swapi.co/api/planets/1/");
        Assertions.assertNotNull(findPlanetResult);
        Assertions.assertEquals("Tatooine", findPlanetResult.getName());


        var findPlanetResultWithNull = integrationService.findPlanet(null);
        Assertions.assertNull(findPlanetResultWithNull);
    }

    @Test
    void should_findRequiredHomeWord() {
        var findRequiredHomeWordResult = integrationService.findRequiredHomeWord("https://swapi.co/api/planets/1/", "Tatooine");
        Assertions.assertNotNull(findRequiredHomeWordResult);
        Assertions.assertEquals("Tatooine", findRequiredHomeWordResult.getName());

        var findRequiredHomeWordResultWithNull = integrationService.findRequiredHomeWord(null, null);
        Assertions.assertNull(findRequiredHomeWordResultWithNull);
    }

    @Test
    void should_findPeopleByCriteria() {
        var queryCriteria = QueryCriteriaDto.builder().characterPhrase("J").planetName("Tatooine").build();
        var results = integrationService.findPeopleByCriteria(queryCriteria);

        /*
         * 2 films and max 3 iteration = 6 items
         * 6 items * 3 list of films = 18
         * */
        Assertions.assertNotNull(results);
        Assertions.assertEquals(18, results.size());
    }

}
