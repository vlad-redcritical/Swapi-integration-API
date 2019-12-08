package pl.softwareplant.api.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.softwareplant.api.client.dto.CharacterDetailsResults;
import pl.softwareplant.api.persistence.entity.Report;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.Objects;

import static org.mockito.Mockito.mock;

class UtilsTests {

    @Test
    void parseIdFromUrl_test() {
        var parseIdFromUrlNull = StringUtility.parseIdFromUrl(null);
        Assertions.assertNull(parseIdFromUrlNull);

        var parseUrlExampleWrong = StringUtility.parseIdFromUrl("");
        Assertions.assertNull(parseUrlExampleWrong);

        var parseUrlExampleTrue = StringUtility.parseIdFromUrl("https://swapi.co/api/planets/1/");
        Assertions.assertEquals(1L, parseUrlExampleTrue);
    }

    @Test
    void pageCounter_test() {
        var pageCounterNull = PageCounterUtils.count(null, null, null);
        Assertions.assertEquals(0, pageCounterNull);

        var pageCounterWithZeroIter = PageCounterUtils.count(30, 10, 3);
        Assertions.assertEquals(0, pageCounterWithZeroIter);

        var pageCounterWithIter = PageCounterUtils.count(30, 10, 1);
        Assertions.assertEquals(3, pageCounterWithIter);
    }

    @Test
    void buildReportEntity_test() {
        var queryDto = QueryCriteriaDto.builder().characterPhrase("Jo").planetName("Tatooine").build();
        var resultOfBuildNonNull = MapperBuildUtils.buildReportEntity(1L, queryDto);
        var expectedReport = Report.builder().id(1L).characterPhrase(queryDto.getCharacterPhrase()).planetName(queryDto.getPlanetName()).build();
        Assertions.assertEquals(expectedReport, resultOfBuildNonNull);


        var resultOfBuildNull = MapperBuildUtils.buildReportEntity(null, null);

        Assertions.assertNull(resultOfBuildNull);
    }

    @Test
    void buildReportDetailsEntity_test() {
        var characterDetailsMock = mock(CharacterDetailsResults.class);
        var reportMock = mock(Report.class);
        var resultOfBuildNonNull = MapperBuildUtils.buildReportDetailsEntity(characterDetailsMock, reportMock);
        Assertions.assertEquals(0, Objects.requireNonNull(resultOfBuildNonNull).getCharacterId());
        Assertions.assertEquals(0, Objects.requireNonNull(resultOfBuildNonNull).getFilmId());
        Assertions.assertNull( Objects.requireNonNull(resultOfBuildNonNull).getId());
        Assertions.assertEquals(0, Objects.requireNonNull(resultOfBuildNonNull).getPlanetId());

        var resultOfBuildNull = MapperBuildUtils.buildReportDetailsEntity(null, null);
        Assertions.assertNull(resultOfBuildNull);
    }

}
