package pl.softwareplant.api.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.softwareplant.api.persistence.entity.Report;
import pl.softwareplant.api.persistence.repository.ReportDetailsRepository;
import pl.softwareplant.api.persistence.repository.ReportRepository;
import pl.softwareplant.api.service.ReportService;
import pl.softwareplant.api.service.impl.ReportServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ReportServiceTests {

    @InjectMocks
    private ReportService reportService = new ReportServiceImpl();

    @Mock
    ReportRepository reportRepository;

    @Mock
    ReportDetailsRepository reportDetailsRepository;

    @BeforeEach
    void setMockOutput() {

        var report = Report.builder().id(1L).planetName("Tatooine").characterPhrase("Jo").build();

        lenient().when(reportRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(report));
    }


    @Test
    void should_return_report_by_id() {
        var report = Report.builder().id(1L).planetName("Tatooine").characterPhrase("Jo").build();
        when(reportRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(report));

        var reportResultOptional = reportService.getReportById(1L);
        Assertions.assertTrue(reportResultOptional.isPresent());
        Assertions.assertEquals(report, reportResultOptional.get());
    }

    @Test
    void should_getAllReports() {
        var report = mock(Report.class);
        when(reportRepository.findAll()).thenReturn(List.of(report, report, report));

        var reportResults = reportService.getAllReports();

        Assertions.assertNotNull(reportResults);
        Assertions.assertEquals(3, reportResults.size());
        Assertions.assertFalse(reportResults.isEmpty());
    }
}
