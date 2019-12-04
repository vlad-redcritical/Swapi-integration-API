package pl.softwareplant.api.client.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PeopleDto {
    Integer count;
    String next;
    String previous;

    List<CharacterDetails> results;
}
