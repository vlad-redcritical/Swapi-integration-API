package pl.softwareplant.api.client.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.softwareplant.api.client.SwapiClient;
import pl.softwareplant.api.client.dto.CharacterDetails;
import pl.softwareplant.api.client.dto.FilmDetailsDto;
import pl.softwareplant.api.client.dto.HomeWorldPlanetDto;
import pl.softwareplant.api.client.dto.PeopleDto;
import pl.softwareplant.api.client.service.IntegrationService;
import pl.softwareplant.api.client.service.dto.FunctionalPair;
import pl.softwareplant.api.utils.PageCounterUtils;
import pl.softwareplant.api.utils.StringUtility;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class IntegrationServiceImpl implements IntegrationService {


    @Autowired
    SwapiClient swapiClient;

    @Autowired
    Cache<Integer, String> planetsCache;

    @Autowired
    Cache<Integer, String> filmsCache;

    @Value("${search.client.default.startPage:1}")
    private Integer clientDefaultStartPage;


    @Value("${search.client.default.perPage:10}")
    private Integer clientDefaultPerPage;

    @Override

    public PeopleDto findPeopleByCriteria(QueryCriteriaDto queryCriteriaDto) {

        FunctionalPair functionalPairResults = executeInitCall.andThen(maxIterationCounter).apply(queryCriteriaDto);

        List<CharacterDetails> characterDetailsList = iterateAndCollectCharacters.apply(queryCriteriaDto, functionalPairResults.maxPossibleIteration());
        characterDetailsList.addAll(functionalPairResults.firstCallInitResult().getResults());

        filterByPlanerName.apply(queryCriteriaDto, characterDetailsList).forEach(System.out::println);



        return null;
    }

    @Override
    public HomeWorldPlanetDto findRequiredHomeWord(String homeWordUrl, String homeWordSearchCriteria) throws FeignException {
        final Integer idHomeWord = StringUtility.parseIdFromUrl(homeWordUrl);

        try {
            return swapiClient.getHomeWordById(idHomeWord, homeWordSearchCriteria);
        } catch (FeignException e) {
            log.error("Not Found Exception");
        }
        return null;
    }

    @Override
    public FilmDetailsDto findFilm(String filmUrl) {
        final Integer filmId = StringUtility.parseIdFromUrl(filmUrl);
        try {
            return swapiClient.getFilm(filmId);
        } catch (FeignException e) {
            log.error("Not Found Exception");
        }

        return null;
    }


    /* ----- Functions ------ */

    /**
     * Execute initial call to swapi API and return the results
     */
    private Function<QueryCriteriaDto, PeopleDto> executeInitCall = (queryCriteriaDto ->
            swapiClient.getPeopleFromPage(clientDefaultStartPage, queryCriteriaDto.getCharacterPhrase())
    );


    /**
     * Try to calculate, how many iteration we can make
     */
    private Function<PeopleDto, FunctionalPair> maxIterationCounter = (peopleDto -> FunctionalPair.builder().firstCallInitResult(peopleDto)
            .maxPossibleIteration(PageCounterUtils.count(peopleDto.getCount(), clientDefaultPerPage, clientDefaultStartPage))
            .build()
    );

    /**
     * All peoples from api by search criteria
     *
     * @param QueryCriteriaDto - search criteria
     * @param Integer - max possible iteration
     * @return List<CharacterDetails>
     */
    private BiFunction<QueryCriteriaDto, Integer, List<CharacterDetails>> iterateAndCollectCharacters = ((queryCriteriaDto, maxIterationInt) ->
            /*Iterator start from (x) range(startInclusive -> endInclusive)*/
            IntStream.rangeClosed(2, maxIterationInt)
                    .parallel()
                    .mapToObj(i ->
                            swapiClient.getPeopleFromPage(i, queryCriteriaDto.getCharacterPhrase()).getResults()
                    )
                    .flatMap(Collection::parallelStream)
                    .collect(Collectors.toList())
    );

    /**
     * Filter : Iterate by characters and try to find results by search criteria - planet name
     */
    private BiFunction<QueryCriteriaDto, List<CharacterDetails>, List<CharacterDetails>> filterByPlanerName = ((criteria, listOfChatacters) ->
            listOfChatacters
                    .parallelStream()
                    .filter(characterDetails ->
                            Objects.nonNull(findRequiredHomeWord(characterDetails.getHomeworld(), criteria.getPlanetName()))
                    ).collect(Collectors.toList())
    );


}
