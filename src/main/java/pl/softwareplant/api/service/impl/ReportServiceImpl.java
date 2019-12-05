package pl.softwareplant.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.softwareplant.api.client.dto.CharacterDetailsResults;
import pl.softwareplant.api.persistence.entity.Report;
import pl.softwareplant.api.persistence.entity.ReportDetails;
import pl.softwareplant.api.persistence.repository.ReportDetailsRepository;
import pl.softwareplant.api.persistence.repository.ReportRepository;
import pl.softwareplant.api.service.ReportService;
import pl.softwareplant.api.web.dto.QueryCriteriaDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportDetailsRepository reportDetailsRepository;

    @Override
    public Optional<Report> getReportById(Long reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public void deleteReportById(Long reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    public void deleteAllReports() {
        reportRepository.deleteAll();
    }

    @Override
    public void createReport(List<CharacterDetailsResults> resultsList, QueryCriteriaDto queryCriteria, Long reportId) {
        Optional<Report> reportOptional = getReportById(reportId);

        /*
         * If report in the same ID already exists, need to delete old one, and create new one.
         * */
        if (reportOptional.isPresent()) {
            deleteReportById(reportId);
        }

        Report reportEntity = reportRepository.save(buildReportEntity(reportId, queryCriteria));

        List<ReportDetails> reportDetailsList = resultsList.parallelStream()
                .map(details -> buildReportDetailsEntity(details, reportEntity))
                .collect(Collectors.toList());

        reportDetailsRepository.saveAll(reportDetailsList);

    }

    private Report buildReportEntity(Long reportId, QueryCriteriaDto criteriaDto) {
        return Report.builder()
                .id(reportId)
                .characterPhrase(criteriaDto.getCharacterPhrase())
                .planetName(criteriaDto.getPlanetName())
                .build();
    }


    private ReportDetails buildReportDetailsEntity(CharacterDetailsResults result, Report reportEntity) {
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
