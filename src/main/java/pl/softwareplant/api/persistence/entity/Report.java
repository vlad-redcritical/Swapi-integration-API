package pl.softwareplant.api.persistence.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "report")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"report_id", "query_criteria_character_phrase", "query_criteria_planet_name"})
public class Report {

    @Id
    @JsonProperty("report_id")
    Long id;

    @Column(nullable = false)
    @JsonProperty("query_criteria_character_phrase")
    String characterPhrase;

    @Column(nullable = false)
    @JsonProperty("query_criteria_planet_name")
    String planetName;

    @JsonProperty("result")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "report")
    List<ReportDetails> reportDetails;
}
