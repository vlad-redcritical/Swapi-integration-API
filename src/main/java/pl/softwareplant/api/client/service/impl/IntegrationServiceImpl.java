package pl.softwareplant.api.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.softwareplant.api.client.SwapiClient;
import pl.softwareplant.api.client.dto.PeopleDto;
import pl.softwareplant.api.client.service.IntegrationService;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

@Service
public class IntegrationServiceImpl implements IntegrationService {


    @Autowired
    SwapiClient swapiClient;


    @Override
    public PeopleDto findPeopleByCriteria(QueryCriteriaDto queryCriteriaDto) {
        PeopleDto results = swapiClient.getPeopleFromPage(1, queryCriteriaDto.getCharacterPhrase());
        return null;
    }


}
