package pl.softwareplant.api.client.service.impl;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.softwareplant.api.client.SwapiClient;
import pl.softwareplant.api.client.dto.CharacterDetails;
import pl.softwareplant.api.client.dto.HomeWorldDto;
import pl.softwareplant.api.client.dto.PeopleDto;
import pl.softwareplant.api.client.service.IntegrationService;
import pl.softwareplant.api.utils.PageCounterUtils;
import pl.softwareplant.api.utils.StringUtility;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class IntegrationServiceImpl implements IntegrationService {


    @Autowired
    SwapiClient swapiClient;

    private final Integer DEFAULT_START_PAGE = 1;

    private final Integer DEFAULT_PER_PAGE = 10;


    @Override
    public PeopleDto findPeopleByCriteria(QueryCriteriaDto queryCriteriaDto) {
        PeopleDto results = swapiClient.getPeopleFromPage(DEFAULT_START_PAGE, queryCriteriaDto.getCharacterPhrase());

        Integer maxRangeToIteration = PageCounterUtils.count(results.getCount(), DEFAULT_PER_PAGE, DEFAULT_START_PAGE);


        /*Iterator start from (x) range(startInclusive -> endInclusive)*/
        List<CharacterDetails> peopleDtoList = IntStream.rangeClosed(2, maxRangeToIteration)
                .parallel()
                .mapToObj(i ->
                        swapiClient.getPeopleFromPage(i, queryCriteriaDto.getCharacterPhrase()).getResults()
                )
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());

        peopleDtoList.addAll(results.getResults());


        peopleDtoList.parallelStream()
                .filter(characterDetails ->
                        !findRequiredHomeWord(characterDetails.getHomeworld(), queryCriteriaDto.getPlanetName()).equals(HomeWorldDto.builder().build())
                ).collect(Collectors.toList()).forEach(System.out::println);


        return null;
    }

    @Override
    public HomeWorldDto findRequiredHomeWord(String homeWordUrl, String homeWordSearchCriteria) throws FeignException {
        final Integer idHomeWord = StringUtility.parseIdFromUrl(homeWordUrl);
        try {
            return swapiClient.getHomeWordById(idHomeWord, homeWordSearchCriteria);
        } catch (FeignException e) {
            log.error("Not Found Exception");
        }
        return HomeWorldDto.builder().build();
    }
}
