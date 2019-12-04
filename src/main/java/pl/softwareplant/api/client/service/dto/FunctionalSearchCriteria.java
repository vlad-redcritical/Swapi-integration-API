package pl.softwareplant.api.client.service.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import pl.softwareplant.api.client.dto.CharacterDetails;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class FunctionalSearchCriteria {

    String characterPhrase;

    String planetName;

    List<CharacterDetails> characterDetailsList;


}
