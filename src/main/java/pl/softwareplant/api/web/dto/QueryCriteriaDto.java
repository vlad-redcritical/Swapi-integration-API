package pl.softwareplant.api.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryCriteriaDto {

    @NotNull(message = "query_criteria_character_phrase can't be a null!")
    @NotEmpty(message = "query_criteria_character_phrase can't be empty!")
    @JsonProperty("query_criteria_character_phrase")
    String characterPhrase;

    @NotNull(message = "query_criteria_planet_name can't be a null!")
    @NotEmpty(message = "query_criteria_planet_name can't be empty!")
    @JsonProperty("query_criteria_planet_name")
    String planetName;

}
