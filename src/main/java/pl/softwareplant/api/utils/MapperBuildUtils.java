package pl.softwareplant.api.utils;

import lombok.experimental.UtilityClass;
import pl.softwareplant.api.client.dto.CharacterDetailsResults;
import pl.softwareplant.api.persistence.entity.Report;
import pl.softwareplant.api.persistence.entity.ReportDetails;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.Objects;

@UtilityClass
public class MapperBuildUtils {
    public Report buildReportEntity(Long reportId, QueryCriteriaDto criteriaDto) {
        if (Objects.isNull(criteriaDto) || Objects.isNull(reportId))
            return null;

        return Report.builder()
                .id(reportId)
                .characterPhrase(criteriaDto.getCharacterPhrase())
                .planetName(criteriaDto.getPlanetName())
                .build();
    }


    public ReportDetails buildReportDetailsEntity(CharacterDetailsResults result, Report reportEntity) {
        if (Objects.isNull(result) || Objects.isNull(reportEntity))
            return null;

        return ReportDetails.builder()
                .filmId(result.filmId())
                .filmName(result.filmName())
                .characterId(result.characterId())
                .characterName(result.characterName())
                .planetId(result.planetId())
                .planetName(result.planetName())
                .report(reportEntity).build();
    }
}
