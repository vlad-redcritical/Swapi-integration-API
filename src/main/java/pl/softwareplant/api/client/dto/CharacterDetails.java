package pl.softwareplant.api.client.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDetails {
    String name;

    String homeworld;

    List<String> films;

    String url;
}
