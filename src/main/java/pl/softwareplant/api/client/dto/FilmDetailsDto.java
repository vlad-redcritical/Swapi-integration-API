package pl.softwareplant.api.client.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FilmDetailsDto {
    @Builder.Default
    String title = "";
}
