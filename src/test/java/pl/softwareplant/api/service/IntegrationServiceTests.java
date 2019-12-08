package pl.softwareplant.api.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.softwareplant.api.client.SwapiClient;
import pl.softwareplant.api.client.service.IntegrationService;
import pl.softwareplant.api.client.service.impl.IntegrationServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class IntegrationServiceTests {

    @InjectMocks
    IntegrationService integrationService = new IntegrationServiceImpl();

    @Mock
    SwapiClient swapiClient;


    @Mock
    Cache<String, String> planetHomeWordCache;

    @Mock
    Cache<String, String> filmsDetailsCache;

    @Test
    void should_findFilm() {
        var findFilmResult = integrationService.findFilm("https://swapi.co/api/films/1/");

        Assertions.assertNull(findFilmResult);

        var findFilmResultNull = integrationService.findFilm(null);
        Assertions.assertNull(findFilmResultNull);
    }

    @Test
    void should_findPlanet() {
        var findPlanetResult = integrationService.findPlanet("https://swapi.co/api/planets/1/");
        Assertions.assertNull(findPlanetResult);


        var findPlanetResultWithNull = integrationService.findPlanet(null);
        Assertions.assertNull(findPlanetResultWithNull);
    }

    @Test
    void should_findRequiredHomeWord(){
        var findRequiredHomeWordResult = integrationService.findRequiredHomeWord("https://swapi.co/api/planets/1/", "Tatooine");
        Assertions.assertNull(findRequiredHomeWordResult);

        var findRequiredHomeWordResultWithNull = integrationService.findRequiredHomeWord(null,  null);
        Assertions.assertNull(findRequiredHomeWordResultWithNull);
    }

}
