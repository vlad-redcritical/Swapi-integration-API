package pl.softwareplant.api.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "report_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "report_id")
    Report report;

    @Column(nullable = false, name = "film_id")
    Long filmId;

    @Column(nullable = false, name = "film_name")
    String filmName;

    @Column(nullable = false, name = "character_id")
    Long characterId;

    @Column(nullable = false, name = "character_name")
    String characterName;

    @Column(nullable = false, name = "planet_id")
    Long planetId;

    @Column(nullable = false, name = "planet_name")
    String planetName;
}
