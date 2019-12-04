package pl.softwareplant.api.client.service.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import pl.softwareplant.api.client.dto.PeopleDto;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class FunctionalPair {
    Integer maxPossibleIteration;

    PeopleDto firstCallInitResult;
}
