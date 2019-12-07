package pl.softwareplant.api.client.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.softwareplant.api.aop.ExecutionTimeLogger;
import pl.softwareplant.api.client.SwapiClient;
import pl.softwareplant.api.client.dto.*;
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
    Cache<String, String> planetHomeWordCache;

    @Autowired
    Cache<String, String> filmsDetailsCache;

    @Autowired
    SwapiClient swapiClient;

    @Value("${search.client.default.startPage:1}")
    private Integer clientDefaultStartPage;


    @Value("${search.client.default.perPage:10}")
    private Integer clientDefaultPerPage;

    @ExecutionTimeLogger
    @Override
    public List<CharacterDetailsResults> findPeopleByCriteria(QueryCriteriaDto queryCriteriaDto) {

        FunctionalPair functionalPairResults = executeInitCall.andThen(maxIterationCounter).apply(queryCriteriaDto);

        List<CharacterDetails> characterDetailsList = iterateAndCollectCharacters.apply(queryCriteriaDto, functionalPairResults.maxPossibleIteration());
        characterDetailsList.addAll(functionalPairResults.firstCallInitResult().getResults());

        return filterByPlanetName.andThen(iterateAndCollectInfo).apply(queryCriteriaDto, characterDetailsList);
    }

    @Override
    public HomeWorldPlanetDto findRequiredHomeWord(String homeWordUrl, String homeWordSearchCriteria) {
        try {
            HomeWorldPlanetDto homeWordResult = swapiClient.getHomeWordById(StringUtility.parseIdFromUrl(homeWordUrl), homeWordSearchCriteria);
            /*
             * If response from client not eq null and homeword planet not in the cache, put value into cache
             * */
            if (Objects.nonNull(homeWordResult) && Objects.isNull(planetHomeWordCache.getIfPresent(homeWordUrl))) {
                planetHomeWordCache.put(homeWordUrl, homeWordResult.getName());
            }

            return homeWordResult;
        } catch (FeignException ex) {
            log.error("Not Found Exception : homeWordUrl: {}, homeWordSearchCriteria: {}, exception: {}", homeWordUrl, homeWordSearchCriteria, ex);
            return null;
        }
    }

    @Override
    public HomeWorldPlanetDto findPlanet(String homeWordUrl) {

        /*
         * If planet name already exists in the cache, why need to ask once-again about that? We can get it from the cache..
         * */
        final String planetName = planetHomeWordCache.getIfPresent(homeWordUrl);
        if (Objects.nonNull(planetName)) {
            return HomeWorldPlanetDto.builder().name(planetName).build();
        }

        try {
            return swapiClient.getPlanet(StringUtility.parseIdFromUrl(homeWordUrl));
        } catch (FeignException ex) {
            log.error("Not Found Exception : homeWordUrl: {}, exception: {}", homeWordUrl, ex);
            return null;
        }
    }

    @Override
    public FilmDetailsDto findFilm(String filmUrl) {


        FilmDetailsDto filmResult = swapiClient.getFilm(StringUtility.parseIdFromUrl(filmUrl));
        /*
         * If response from client not eq null and film title not in the cache, put value into cache
         * */
        if (Objects.nonNull(filmResult) && Objects.isNull(planetHomeWordCache.getIfPresent(filmUrl))) {
            filmsDetailsCache.put(filmUrl, filmResult.getTitle());
        }

        try {
            return filmResult;
        } catch (FeignException ex) {
            log.error("Not Found Exception: filmUrl: {}, exception: {}", filmUrl, ex);
            return null;
        }
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
                    .mapToObj(i -> swapiClient.getPeopleFromPage(i, queryCriteriaDto.getCharacterPhrase()).getResults())
                    .flatMap(Collection::parallelStream)
                    .collect(Collectors.toList())
    );

    /**
     * Filter : Iterate by characters and try to find results by search criteria - planet name
     */
    private BiFunction<QueryCriteriaDto, List<CharacterDetails>, List<CharacterDetails>> filterByPlanetName = ((criteria, listOfCharacters) ->
            listOfCharacters
                    .parallelStream()
                    .filter(characterDetails ->
                            Objects.nonNull(findRequiredHomeWord(characterDetails.getHomeworld(), criteria.getPlanetName()))
                    ).collect(Collectors.toList())
    );


    /**
     * Build splitter film result, with required data
     */
    private BiFunction<CharacterDetails, String, CharacterDetailsResults> buildObjectResult = ((characterDetails, filmUrl) ->
            CharacterDetailsResults.builder()
                    .filmId(StringUtility.parseIdFromUrl(filmUrl))
                    .filmName(findFilm(filmUrl).getTitle())
                    .characterId(StringUtility.parseIdFromUrl(characterDetails.getUrl()))
                    .characterName(characterDetails.getName())
                    .planetId(StringUtility.parseIdFromUrl(characterDetails.getHomeworld()))
                    .planetName(findPlanet(characterDetails.getHomeworld()).getName())
                    .build()

    );

    /**
     * Iterate by character, and provide qualifier per film
     */
    private Function<List<CharacterDetails>, List<CharacterDetailsResults>> iterateAndCollectInfo = (characterDetailsList ->
            characterDetailsList
                    .parallelStream()
                    .map(
                            characterDetails -> characterDetails.getFilms()
                                    .parallelStream()
                                    .map(
                                            filmUrl -> buildObjectResult.apply(characterDetails, filmUrl)
                                    )
                                    .collect(Collectors.toList())
                    ).flatMap(Collection::parallelStream)
                    .collect(Collectors.toList())
    );

}
