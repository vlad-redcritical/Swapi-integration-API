package pl.softwareplant.api.client.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Accessors(fluent = true, chain = true)
@AllArgsConstructor
public class CharacterDetailsResults {
    Long filmId;

    String filmName;

    Long characterId;

    String characterName;

    Long planetId;

    String planetName;
}
