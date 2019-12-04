package pl.softwareplant.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.softwareplant.api.client.config.ClientConfig;
import pl.softwareplant.api.client.dto.PeopleDto;

@FeignClient(value = "swapiclient", url = "https://swapi.co/api", configuration = ClientConfig.class)
public interface SwapiClient {

    @GetMapping(value = "/people/?page={pageNum}&search={characterPhrase}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    PeopleDto getPeopleFromPage(@PathVariable Integer pageNum, @PathVariable String characterPhrase);

}
